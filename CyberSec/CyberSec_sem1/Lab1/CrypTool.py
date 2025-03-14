import math
import string
import numpy as np
import matplotlib.pyplot as plt

def read_text_file(file_path: str) -> str:
    """
    Reads the content of a text file and returns it as a string
    """
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            text = f.read()
        return text
    except (IOError, OSError) as e:
        print(f"Error reading file {file_path}: {e}")
        return ""


def preprocess_text(text: str) -> str:
    """
    Converts text to uppercase and removes non-alphabetical characters
    """
    # Example: remove everything except A-Z (no diacritics)
    filtered_text = []
    for ch in text.upper():
        if ch in string.ascii_uppercase:
            filtered_text.append(ch)
    return "".join(filtered_text)


def compute_frequency_distribution(text: str) -> dict:
    """
    Computes the frequency (count) of each character in the text
    Returns a dictionary {character: count}
    """
    freq_dict = {}
    for ch in text:
        freq_dict[ch] = freq_dict.get(ch, 0) + 1
    return freq_dict


def compute_entropy(text: str) -> float:
    """
    H = - sum( p(c) * log2(p(c)) ), for each character c in text
    """
    freq_dict = compute_frequency_distribution(text)
    text_length = len(text)
    if text_length == 0:
        return 0.0  # Avoid division by zero
    entropy = 0.0
    for ch in freq_dict:
        p = freq_dict[ch] / text_length
        entropy -= p * math.log2(p)
    return entropy


def plot_histogram(freq_dict: dict, title: str = "Histogram"):
    """
    Plots a histogram (bar chart) of character frequencies
    freq_dict is expected to be {character: count}
    """
    characters = list(freq_dict.keys())
    counts = list(freq_dict.values())
    
    plt.figure()
    plt.bar(characters, counts)
    plt.title(title)
    plt.xlabel("Characters")
    plt.ylabel("Frequency")
    plt.show()


def caesar_cipher(text: str, key: int) -> str:
    """
    Implements a simple Caesar Cipher
    """
    ciphertext = []
    for ch in text:
        # 'A' -> 65 in ASCII
        shifted = ((ord(ch) - ord('A') + key) % 26) + ord('A')
        ciphertext.append(chr(shifted))
    return "".join(ciphertext)


def vigenere_cipher(text: str, key: str, encrypt: bool = True) -> str:
    """
    Encrypts or decrypts text using the Vigenere Cipher
    If encrypt=True, performs encryption
    """
    result = []
    key = key.upper()
    key_index = 0
    key_length = len(key)
    for ch in text:
        shift = ord(key[key_index]) - ord('A')
        if not encrypt:
            shift = -shift
        shifted = ((ord(ch) - ord('A') + shift) % 26) + ord('A')
        result.append(chr(shifted))
        key_index = (key_index + 1) % key_length
    return "".join(result)


def hill_cipher_encrypt(text: str, matrix: np.ndarray) -> str:
    """
    Encrypts the text using the Hill Cipher with the provided matrix (mod 26):
    - text is divided into blocks of size = matrix.shape[0]
    - matrix is expected to be a square NumPy array
    - This is a simplified implementation without robust checks of invertibility, etc
    """
    n = matrix.shape[0]
    # Pad text if necessary so its length is a multiple of n
    if len(text) % n != 0:
        text += "X" * (n - (len(text) % n))  # adding 'X' as padding
    
    ciphertext = []
    for i in range(0, len(text), n):
        block = text[i:i+n]
        # Convert block to vector
        vector = [ord(ch) - ord('A') for ch in block]
        vector = np.array(vector).reshape(n, 1)
        
        # Encrypt: matrix * vector mod 26
        encrypted_vec = np.dot(matrix, vector) % 26
        
        # Convert back to letters
        for val in encrypted_vec:
            ciphertext.append(chr(int(val[0]) + ord('A')))
    return "".join(ciphertext)


def xor_cipher(text: str, key: str) -> str:
    """
    Encrypts/Decrypts text by XORing each character with the key (repeated)
    Assumes ASCII uppercase letters
    """
    extended_key = (key * ((len(text) // len(key)) + 1))[:len(text)]
    result_chars = []
    for t_ch, k_ch in zip(text, extended_key):
        # XOR the numeric ASCII values
        xored_value = (ord(t_ch) ^ ord(k_ch)) % 256
        # For demonstration, convert back to an uppercase letter if possible
        # or use chr(xored_value) if you want a raw binary -> ASCII mapping.
        # We'll limit to [A-Z] by modulo 26, if you want pure XOR binary, adjust accordingly.
        letter = chr((xored_value % 26) + ord('A'))
        result_chars.append(letter)
    return "".join(result_chars)


def compute_autocorrelation(text: str, max_lag: int = 20) -> list:
    """
    Computes a simple autocorrelation for lags from 1 to max_lag (or up to n-1 if n < max_lag)
    Interprets each character as an integer (0 for 'A', 1 for 'B', etc.)
    Returns a list of autocorrelation values (one per lag)
    """
    if not text or len(text) < 2:
        return [0.0] * max_lag
    
    numeric_values = [ord(ch) - ord('A') for ch in text]
    n = len(numeric_values)
    # We only compute up to the smaller of n-1 or max_lag
    actual_max_lag = min(max_lag, n - 1)
    
    arr = np.array(numeric_values, dtype=float)
    mean_val = np.mean(arr)
    var_val = np.var(arr)
    
    # If variance is zero (all characters identical), autocorrelation is undefined/trivial
    if var_val == 0:
        return [0.0] * actual_max_lag
    
    results = []
    for lag in range(1, actual_max_lag + 1):
        # original: arr[0 : n - lag]
        # shifted:  arr[lag : n]
        original = arr[:-lag]
        shifted = arr[lag:]
        
        # Now both 'original' and 'shifted' should have the same length => n - lag
        if len(original) != len(shifted):
            # In principle, this shouldn't happen with the slicing above,
            # but we keep a safeguard:
            results.append(0.0)
            continue
        
        # Compute autocorrelation for this lag
        corr = np.sum((original - mean_val) * (shifted - mean_val)) / ((n - lag) * var_val)
        results.append(corr)
    
    # If user requested a max_lag larger than n-1, we pad the result with zeros
    if max_lag > actual_max_lag:
        results.extend([0.0] * (max_lag - actual_max_lag))
    
    return results



def main():
    # 1. Read in plaintext samples for Polish, English, and Swedish
    files = ["polish.txt", "english.txt", "swedish.txt"]
    plaintexts = []
    
    for f in files:
        text_data = read_text_file(f)
        text_data = preprocess_text(text_data)
        plaintexts.append(text_data)

    # 2. Compute and compare entropies for the three plaintexts
    entropies = []
    for pt in plaintexts:
        entropies.append(compute_entropy(pt))
    print("Entropies of the three plaintexts:", entropies)

    # 3. Histogram comparisons
    #    Using the first 3 plaintexts as examples.
    for i, pt in enumerate(plaintexts):
        freq_dict = compute_frequency_distribution(pt)
        plot_histogram(freq_dict, title=f"Histogram of Plaintext {i+1}")

    # 4. Encrypt a chosen plaintext with Caesar, Vigenere, and Hill.
    #    For demonstration, pick the first plaintext:
    if plaintexts:
        sample_text = plaintexts[0]
        caesar_key = 3
        vigenere_key = "supertajnehaslo"
        
        # For Hill, define a 2x2 (or NxN) matrix. Must be invertible mod 26 for decryption.
        hill_matrix = np.array([[3, 3],
                                [2, 5]])  # Example matrix with a known invertible structure mod 26.

        caesar_ct = caesar_cipher(sample_text, caesar_key)
        vigenere_ct = vigenere_cipher(sample_text, vigenere_key, encrypt=True)
        hill_ct = hill_cipher_encrypt(sample_text, hill_matrix)

        # Print and compare entropies
        print("Plaintext Entropy:", compute_entropy(sample_text))
        print("Caesar Cipher Entropy:", compute_entropy(caesar_ct))
        print("Vigenere Cipher Entropy:", compute_entropy(vigenere_ct))
        print("Hill Cipher Entropy:", compute_entropy(hill_ct))

        # Plot histograms of ciphertexts
        plot_histogram(compute_frequency_distribution(caesar_ct), "Caesar Cipher Histogram")
        plot_histogram(compute_frequency_distribution(vigenere_ct), "Vigenere Cipher Histogram")
        plot_histogram(compute_frequency_distribution(hill_ct), "Hill Cipher Histogram")

    # 5. Autocorrelation analysis of XOR and Vigenere for different key lengths.
    key_lengths = [2, 4, 8]
    for kl in key_lengths:
        key_str = "A" * kl  # Example repeated 'A' key (replace with random or real key)
        
        xor_ct = xor_cipher(sample_text, key_str)
        xor_corr = compute_autocorrelation(xor_ct, max_lag=20)
        print(f"XOR Cipher Autocorrelation (key length={kl}):", xor_corr)

        vig_ct = vigenere_cipher(sample_text, key_str, encrypt=True)
        vig_corr = compute_autocorrelation(vig_ct, max_lag=20)
        print(f"Vigenere Autocorrelation (key length={kl}):", vig_corr)

        plt.figure()
        plt.plot(range(1, 21), xor_corr)
        plt.title(f"XOR Autocorrelation (key length={kl})")
        plt.xlabel("Lag")
        plt.ylabel("Autocorrelation")
        plt.show()

        plt.figure()
        plt.plot(range(1, 21), vig_corr)
        plt.title(f"Vigenere Autocorrelation (key length={kl})")
        plt.xlabel("Lag")
        plt.ylabel("Autocorrelation")
        plt.show()


if __name__ == "__main__":
    main()
