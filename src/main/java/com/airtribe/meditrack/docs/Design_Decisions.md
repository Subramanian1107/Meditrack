# MediTrack – Design Decisions

## 1. Project Overview

MediTrack is a console-based healthcare management system designed to manage doctors, patients, appointments, and billing. The system also includes AI-assisted recommendations for doctor selection and appointment scheduling.

The project follows a modular architecture to separate business logic, data models, and utilities.

---

# 2. Architectural Design

The project follows a **layered architecture**:

```
Main (CLI Layer)
     |
Service Layer
     |
Entity Layer
     |
Utility / Observer / Factory
```

### Layers

**CLI Layer**

* Implemented in `Main.java`
* Handles user input and menu navigation.

**Service Layer**

* Contains business logic.
* Responsible for managing doctors, patients, and appointments.

Classes:

* `DoctorService`
* `PatientService`
* `AppointmentService`

**Entity Layer**

* Contains domain models.

Classes:

* `Doctor`
* `Patient`
* `Appointment`
* `BillSummary`

**Utility Layer**

* Contains helper classes and factories.

Classes:

* `AIHelper`
* `BillFactory`

---

# 3. Design Patterns Used

## 3.1 Observer Pattern

The Observer Pattern is used for sending appointment reminders.

**Observer Interface**

```
ReminderObserver
```

**Concrete Observers**

* `ConsoleReminderObserver`
* `EmailReminderObserver`
* `WhatsAppReminderObserver`

When an appointment is created, confirmed, or cancelled, observers are notified to simulate different reminder channels.

Benefits:

* Loose coupling
* Easy to add new notification systems

---

## 3.2 Factory Pattern

The Factory Pattern is used to generate bills using `BillFactory`.

```
BillFactory.createBill(type)
```

Supported bill types:

* Consultation bill
* Procedure bill

Benefits:

* Centralized object creation
* Easy to extend for new billing types

---

## 3.3 Service Layer Pattern

Business logic is encapsulated inside service classes instead of the main class.

Example:

```
DoctorService
PatientService
AppointmentService
```

Benefits:

* Separation of concerns
* Easier testing
* Better maintainability

---

# 4. AI-Assisted Features

The system includes simple rule-based AI features implemented in `AIHelper`.

## 4.1 Symptom-Based Specialization Recommendation

```
recommendSpecialization(symptom)
```

Maps patient symptoms to a recommended medical specialization.

Example:

```
"chest pain" → CARDIOLOGY
"skin rash" → DERMATOLOGY
```

---

## 4.2 Doctor Recommendation

```
recommendDoctors(symptom, doctors)
```

Returns a filtered list of doctors whose specialization matches the predicted specialization.

---

## 4.3 Smart Appointment Slot Suggestion

```
suggestSlots(doctor, appointments)
```

Analyzes the doctor's existing appointments and suggests available slots.

---

## 4.4 Least Busy Doctor Recommendation

```
recommendLeastBusyDoctor(doctors, appointments)
```

Identifies the doctor with the lowest number of active appointments.

---

## 4.5 Affordable Doctor Recommendation

```
recommendAffordableDoctors(doctors, maxFee)
```

Filters doctors whose consultation fee is within the patient's budget.

---

## 4.6 Peak Hour Detection

```
detectPeakHour(appointments)
```

Determines the hour of the day with the highest number of appointments.

Purpose:

* Identify clinic rush hours
* Useful for scheduling improvements

---

# 5. Data Persistence

Data is stored in **CSV files**.

Files used:

* `doctors.csv`
* `patients.csv`
* `appointments.csv`

Each service class supports:

```
loadFromCsv()
saveToCsv()
```

Benefits:

* Simple persistence
* No external database required

---

# 6. Exception Handling

Custom exceptions improve reliability and clarity.

### InvalidDataException

Thrown when invalid input data is provided.

### AppointmentNotFoundException

Thrown when an appointment cannot be found during operations like cancel or confirm.

---

# 7. Scalability Considerations

The project structure allows easy future extensions:

Possible improvements:

* Replace CSV with a database (MySQL/PostgreSQL)
* Replace CLI with REST APIs
* Add authentication and user roles
* Integrate real AI models for diagnosis recommendations

---

# 8. Key Assumptions

* Each doctor has a unique ID.
* Each patient has a unique ID.
* Appointment times are simplified using current timestamps.
* AI recommendations are rule-based rather than machine learning based.

---

# 9. Future Improvements

Potential enhancements include:

* REST API integration
* Web or mobile frontend
* Real-time notification services
* Machine learning based symptom analysis
* Hospital analytics dashboard

---

# 10. Conclusion

MediTrack demonstrates a clean backend architecture with separation of concerns, design patterns, and simple AI-driven insights. The project focuses on maintainability, extensibility, and demonstrating real-world backend design practices.
