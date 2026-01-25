🥤 Vending Machine (Java)

A Java-based vending machine simulation developed with object-oriented principles.  
The project includes a Swing GUI for interactive use and a Dockerized headless mode for containerized execution.

---

🚀 Features
- Shelf and product position management
- Solid and liquid products
- Stock availability checks
- Coin-based payment system
- Change calculation and return
- Probability-based coin acceptance
- Cancel option with refund
- Custom exception handling
- Swing-based graphical user interface (GUI)
- Headless execution mode via Docker

---

🛠️ Tech Stack
- Java (JDK 17)
- Object-Oriented Programming (OOP)
- Collections (ArrayList)
- Exception handling
- Java Swing (GUI)
- Docker & Docker Compose

---

🧱 Project Structure
- `VendingMachine` – core business logic
- `Shelf`, `Position` – physical structure of the machine
- `Product`, `SolidProduct`, `LiquidProduct`
- `Coin`
- `ProductException`
- `VendingMachineUI` – Swing GUI
- `Main` – headless entry point (used in Docker)

---

▶️ How to Run (GUI – Local)

Requirements:
- Java JDK 17+

Compile and run manually:

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out VendingMachineUI
Or, on Windows, using the provided script:

.\run-gui.bat
🐳 How to Run (Docker – Headless)

Requirements:

Docker

Docker Compose

Build and run manually:

docker compose up --build
Or, on Windows, using the provided script:

.\run-docker.bat
This mode runs the application without a GUI and demonstrates containerized execution.

📌 Notes

The Swing GUI is intentionally executed outside Docker, as desktop UIs are not suitable for containerized execution on Windows.

Docker support is provided for build validation and headless execution.

Environment variables are managed via .env (excluded from version control) and .env.example.

📚 About
This project was developed as an academic exercise with a focus on:

Object-oriented design

State management

Separation of concerns

Practical Docker usage for Java applications
