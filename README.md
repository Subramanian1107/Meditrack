
# 🏥 MediTrack – Clinic Management System (Submission)

MediTrack is a **Java-based console application** for managing clinic operations such as doctors, patients, appointments, billing, analytics, and AI-based assistance.

The project demonstrates **Object-Oriented Design, Design Patterns, File Persistence, and Rule-Based AI features**.

---

# 🚀 Features

## 👨‍⚕️ Doctor Management
- Add doctor
- View doctor list
- Update doctor details
- Delete doctor
- Search doctor by:
  - ID
  - Name
  - Specialization

## 🧑‍🤝‍🧑 Patient Management
- Add patient
- View patient list
- Update patient details
- Delete patient
- Search patient by:
  - ID
  - Name
  - Age

## 📅 Appointment Management
- Book appointment
- View all appointments
- Confirm appointment
- Cancel appointment
- Prevent conflicting schedules

## 💳 Billing System
- Generate bills for appointments
- Bill types:
  - Consultation
  - Procedure
- Implemented using **Factory Design Pattern**

## 🔔 Notification System
Supports multiple reminder channels:
- Console notifications
- Email reminders
- WhatsApp reminders

Implemented using **Observer Design Pattern**.

## 📊 Analytics
Provides insights such as:
- Average doctor consultation fee
- Number of appointments per doctor

## 🤖 AI Features

### Doctor Recommendation
Patients enter symptoms and the system recommends the appropriate specialization and doctors.

Example:

Symptom: chest pain  
AI Recommendation: CARDIOLOGY

### Smart Appointment Slot Suggestion
- Suggests available time slots
- Avoids conflicting appointments
- Helps in faster scheduling

---

# 🏗️ Project Structure

```
meditrack
│
├── constants
│   └── Constants.java
│
├── entity
│   ├── Doctor.java
│   ├── Patient.java
│   ├── Appointment.java
│   ├── BillSummary.java
│   └── Specialization.java
│
├── exception
│   ├── AppointmentNotFoundException.java
│   └── InvalidDataException.java
│
├── observer
│   ├── ReminderObserver.java
│   ├── ConsoleRemainderObserver.java
│   ├── EmailRemainderObserver.java
│   └── WhatsAppRemainderObserver.java
│
├── service
│   ├── DoctorService.java
│   ├── PatientService.java
│   └── AppointmentService.java
│
├── util
│   ├── BillFactory.java
│   └── AiHelper.java
│
└── Main.java
```

---

# 🧠 Design Patterns Used

## Observer Pattern
Used for the notification system.

AppointmentService → Subject  
Observers:
- ConsoleRemainderObserver
- EmailRemainderObserver
- WhatsAppRemainderObserver

Whenever an appointment changes, all observers receive notifications.

---

## Factory Pattern

Implemented in **BillFactory**.

Creates different types of bills:

- CONSULTATION BILL
- PROCEDURE BILL

This keeps billing logic flexible and extendable.

---

## Singleton Pattern

Used for generating unique IDs across the system.

- IdGenerator

Ensures only one instance exists and provides a centralized way to generate unique IDs for doctors, patients, and appointments.

---

# 💾 Data Persistence

Data can be stored and loaded using **CSV files**.

Files used:

- doctors.csv
- patients.csv
- appointments.csv

To load existing data:

```
java Main --loadData
```

To save data:

Use the menu option **Save Data**.

---

# 🛠️ Technologies Used

- Java
- Object-Oriented Programming
- Java Collections
- Java Time API
- File I/O (CSV persistence)

---

# ▶️ How to Run

## Compile

```
javac Main.java
```

## Run

```
java Main
```

## Run with persisted data

```
java Main --loadData
```

---

# 📌 Example Workflow

1. Add doctors
2. Add patients
3. Create appointment
4. AI recommends doctor based on symptoms
5. AI suggests available time slots
6. Confirm appointment
7. Generate bill
8. View analytics

---

# 📈 Future Improvements

Possible enhancements:

- REST API version
- GUI using JavaFX or Spring Boot
- Database integration (MySQL/PostgreSQL)
- Real email/WhatsApp notification integration
- Machine learning based doctor recommendation

---

# 👨‍💻 Author

**Subramanian**  
**Mithun Yentrapati**
**Tushar Khobragade**
