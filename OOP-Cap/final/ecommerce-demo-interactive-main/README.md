## Prerequisites

- **JDK 17** (or newer).
- A POSIX‑style shell (macOS / Linux) or PowerShell on Windows for the `find` command shown below.
- No external build system – a single **JUnit Platform Console** JAR bundled in \`\` is all that is required for the tests.

---

## 1. Compile the application

```bash
# run from the project root
javac -d out "main\model\*.java" "main\cart\*.java" "main\promotions\*.java" "main\catalog\*.java" "main\*.java"
```

The command compiles every source file under `and drops the class files into`.

---

## 2. Compile and execute the test‑suite

```bash
# 1) compile
javac -cp "lib\junit-platform-console-standalone-1.9.3.jar;out" -d out "test\main\model\ProductTest.java" "test\main\cart\CartTest.java"

# 2) run the tests
java -jar lib/junit-platform-console-standalone-1.9.3.jar --class-path out --scan-classpath
```

*Replace the **;** with **:** in the class‑path if you are on Windows.*

---

## 3. Launch the demo

```bash
java -cp out main.Main
```

You should now see an interactive menu that lets you browse products, add items to the cart and proceed to checkout while the promotion engine applies discounts automatically.

---

## 4. Clean up compiled artefacts

```bash
your$ rm -rf out
```

---


