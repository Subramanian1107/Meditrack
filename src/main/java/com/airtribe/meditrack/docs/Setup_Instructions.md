# MediTrack – Setup Instructions

## 1. Prerequisites

Before running the project, ensure the following tools are installed:

* **Java JDK 8 or higher**
* **Git**
* **IDE (recommended)**

    * IntelliJ IDEA
    * VS Code with Java extensions
    * Eclipse

Verify Java installation:

```bash
java -version
javac -version
```

---

# 2. Clone the Repository

Clone the project using Git:

```bash
git clone <repository-url>
cd meditrack
```

---

# 3. Project Structure

```text
meditrack
│
├── src
│   └── main
│       └── java
│           └── com
│               └── airtribe
│                   └── meditrack
│                       ├── constants
│                       ├── entity
│                       ├── exception
│                       ├── observer
│                       ├── service
│                       ├── util
│                       └── Main.java
│
├── doctors.csv
├── patients.csv
├── appointments.csv
│
├── Design_Decisions.md
├── Setup_Instructions.md
└── README.md
```

---

# 4. Compile the Project

From the root directory, compile the Java files:

```bash
javac -d out $(find src -name "*.java")
```

This will create compiled class files inside the `out` directory.

---

# 5. Run the Application

Run the application using:

```bash
java -cp out com.airtribe.meditrack.Main
```

To load existing CSV data on startup:

```bash
java -cp out com.airtribe.meditrack.Main --loadData
```

---

# 6. Using the Application

After running the program, the console menu will appear:

```text
===== MediTrack =====
1. Doctors
2. Patients
3. Appointments
4. Billing
5. Search
6. Analytics
7. Save data
0. Exit
```

### Main Features

Doctors

* Add doctor
* List doctors
* Update doctor
* Delete doctor

Patients

* Add patient
* List patients
* Update patient
* Delete patient

Appointments

* Create appointment
* View appointments
* Cancel appointment
* Confirm appointment

Billing

* Generate consultation or procedure bill

Search

* Search doctor by ID, name, specialization
* Search patient by ID, name, or age

Analytics & AI

* Average doctor fee
* Appointments per doctor
* AI-based doctor recommendation
* Affordable doctor suggestions
* Clinic peak hour detection

---

# 7. Data Persistence

The system stores data in CSV files:

```text
doctors.csv
patients.csv
appointments.csv
```

To save current data:

Select menu option:

```text
7. Save data
```

---

# 8. AI Features

The project includes simple AI-assisted features implemented in `AIHelper`:

* Symptom → Specialization recommendation
* Doctor recommendation based on specialization
* Suggested appointment slots
* Least busy doctor recommendation
* Budget-friendly doctor recommendation
* Clinic peak hour detection

These features help improve appointment scheduling and doctor selection.

---

# 9. Observer Notifications

The system simulates appointment reminders using observers:

* Console reminder
* Email reminder
* WhatsApp reminder

Whenever an appointment is created, confirmed, or cancelled, observers are notified.

---

# 10. Troubleshooting

### Issue: Compilation errors

Ensure the correct package structure:

```text
src/main/java/com/airtribe/meditrack
```

### Issue: CSV not loading

Run with the load flag:

```bash
java -cp out com.airtribe.meditrack.Main --loadData
```

### Issue: Java version errors

Ensure Java version is 8 or higher.

---

# 11. Future Improvements

Possible extensions:

* REST API version using Spring Boot
* Database integration (MySQL/PostgreSQL)
* Web or mobile UI
* Real email/SMS notifications
* Machine learning based diagnosis recommendations

---

# 12. Author

MediTrack Backend System
Developed as part of the Backend Java assignment.
