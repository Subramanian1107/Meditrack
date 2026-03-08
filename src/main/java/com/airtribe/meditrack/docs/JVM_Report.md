# JVM Report – MediTrack Application

## 1. Introduction

The Java Virtual Machine (JVM) is responsible for executing Java programs. It provides platform independence by converting compiled Java bytecode into machine-specific instructions.

In the MediTrack project, the JVM loads, verifies, and executes the compiled classes such as `Main`, `DoctorService`, `PatientService`, and `AppointmentService`.

This report explains how the JVM processes the MediTrack application during execution.

---

# 2. Java Compilation and Execution Flow

The execution of the MediTrack application follows these steps:

```text id="mnfr9g"
Java Source Code (.java)
        ↓
Java Compiler (javac)
        ↓
Bytecode (.class)
        ↓
Java Virtual Machine (JVM)
        ↓
Execution on Operating System
```

### Step 1: Compilation

The Java compiler (`javac`) compiles the source files into bytecode.

Example:

```bash id="fjj3th"
javac -d out $(find src -name "*.java")
```

This generates `.class` files for all project classes.

### Step 2: JVM Execution

The application is executed using:

```bash id="ajg9sb"
java -cp out com.airtribe.meditrack.Main
```

The JVM loads the `Main` class and begins execution from the `main()` method.

---

# 3. JVM Architecture Overview

The JVM consists of several components responsible for executing Java programs.

```text id="ur2e17"
Class Loader
      ↓
Bytecode Verifier
      ↓
Runtime Data Areas
      ↓
Execution Engine
```

---

# 4. Class Loader Subsystem

The Class Loader loads `.class` files into memory when the application starts.

In MediTrack, the following classes are loaded:

* `Main`
* `Doctor`
* `Patient`
* `Appointment`
* `DoctorService`
* `PatientService`
* `AppointmentService`
* `AIHelper`
* `BillFactory`
* Observer classes

The JVM loads classes only when they are needed, which improves memory efficiency.

Types of class loaders involved:

1. **Bootstrap ClassLoader**

    * Loads core Java libraries (`java.lang`, `java.util`, etc.)

2. **Platform ClassLoader**

    * Loads standard Java platform classes.

3. **Application ClassLoader**

    * Loads MediTrack project classes.

---

# 5. Runtime Data Areas

The JVM uses several memory areas during execution.

## 5.1 Heap Memory

Heap memory stores objects created during runtime.

Examples in MediTrack:

```text id="a6dx70"
Doctor objects
Patient objects
Appointment objects
BillSummary objects
```

Example object creation:

```java id="2uiv8n"
Doctor doctor = doctorService.addDoctor(name, age, specialization, experience, fee);
```

All objects are allocated in the heap.

---

## 5.2 Stack Memory

Each thread has its own stack used for method calls.

Example method calls in MediTrack:

```text id="h35tq5"
Main.main()
 → runMenu()
 → doctorMenu()
 → addDoctor()
 → DoctorService.addDoctor()
```

Each method call creates a new **stack frame**.

The stack frame contains:

* Local variables
* Method parameters
* Return values

---

## 5.3 Method Area

The Method Area stores:

* Class metadata
* Static variables
* Method bytecode

Examples from MediTrack:

```text id="01w1py"
Constants.DOCTOR_FILE
Constants.PATIENT_FILE
Constants.APPOINTMENT_FILE
```

These static constants are stored in the Method Area.

---

# 6. Execution Engine

The Execution Engine is responsible for executing bytecode.

It uses two techniques:

### 6.1 Interpreter

The interpreter reads bytecode line-by-line and executes it.

Example:

```java id="0v9n3j"
doctorService.getAllDoctors();
```

---

### 6.2 Just-In-Time (JIT) Compiler

The JIT compiler improves performance by converting frequently used bytecode into native machine code.

For example, frequently used methods like:

```text id="q1vh2u"
getAllDoctors()
getAllPatients()
bookAppointment()
```

may be optimized by the JIT compiler.

---

# 7. Garbage Collection

The JVM automatically manages memory using Garbage Collection.

When objects are no longer referenced, they are removed from memory.

Examples in MediTrack:

* Temporary `BillSummary` objects
* Unused `Appointment` references
* Intermediate AI recommendation lists

Benefits:

* Prevents memory leaks
* Automatic memory management
* Improves application stability

---

# 8. Exception Handling in JVM

The JVM manages exceptions using stack unwinding.

Custom exceptions in MediTrack include:

```text id="v12ys7"
InvalidDataException
AppointmentNotFoundException
```

Example:

```java id="rqy9qk"
try {
    appointmentService.cancelAppointment(id);
} catch (AppointmentNotFoundException e) {
    System.err.println(e.getMessage());
}
```

If an exception occurs, the JVM searches the call stack for a matching handler.

---

# 9. JVM Role in MediTrack Execution

During execution, the JVM performs the following tasks:

1. Loads MediTrack classes
2. Allocates memory for objects
3. Executes service methods
4. Manages method calls using stacks
5. Handles exceptions
6. Performs garbage collection
7. Optimizes performance using JIT

This allows the MediTrack application to run efficiently across different operating systems.

---

# 10. Conclusion

The JVM plays a critical role in executing the MediTrack application by managing memory, executing bytecode, handling exceptions,
