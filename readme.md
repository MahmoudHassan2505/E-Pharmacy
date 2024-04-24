## Class Digrame
```mermaid
classDiagram
direction BT
class Authority {
  ~ long id
  - String authority
  - User user
  - String username
}
class CollegeUseageMedicine {
  - long id
  - long amount
  - CollegeUseages collegeUseages
  - Inventory inventory
}
class CollegeUseages {
  - Date date
  - long id
  - String collegeName
  - List~CollegeUseageMedicine~ collegeUseagesMedicines
}
class Disease {
  - long id
  - String name
}
class Inventory {
  - long amount
  - OrderMedicine orderMedicine
  - long id
}
class Medicine {
  - String arabicname
  - long id
  - String dosageform
  - String strength
  - long barcode
  - String unit
  - String name
  - MedicineCategory medicineCategory
  - List~UseageMedicine~ useageMedicines
  - List~OrderMedicine~ orderMedicines
  - String activeingredient
  - long alertexpired
  - String manufacturer
  - long alertamount
}
class MedicineCategory {
  - long id
  - String name
}
class Notification {
  - String message
  - Medicine medicine
  - long orderid
  - long id
}
class Order {
  - Date dateofsupply
  - long deliveryrequest
  - long supplyrequest
  - long id
  - Supplier supplier
  - List~OrderMedicine~ orderMedicines
}
class OrderMedicine {
  - long id
  - Medicine medicine
  - long amount
  - Date expirydate
  - long price
  - Order order
}
class Patient {
  - long student_id
  - String collegeName
  - String gender
  - boolean chronic
  - String phone_number
  - String name
  - long nationalid
  - List~Disease~ disease
  - String level
  - Integer age
}
class Prescription {
  - boolean is_allowed
  - String diagnosis
  - long id
  - List~Medicine~ medicines
  - PrescriptionCategory prsPrescriptionCategory
  - Patient patient
}
class PrescriptionCategory {
  - long id
  - String name
}
class Supplier {
  - long id
  - String name
}
class Unit {
  - long type
  - long id
  - String name
}
class Useage {
  - Prescription prescription
  - Date date
  - List~UseageMedicine~ useageMedicines
  - long id
}
class UseageMedicine {
  - Inventory inventory
  - long amount
  - Useage useage
  - long id
  - long price
  - Medicine medicine
}
class User {
  - String nationalId
  - List~Authority~ authority
  - String name
  - String password
  - String username
  - String phone
  - short enabled
}

Authority "1" *--> "user 1" User 
CollegeUseageMedicine "1" *--> "collegeUseages 1" CollegeUseages 
CollegeUseageMedicine "1" *--> "inventory 1" Inventory 
CollegeUseages "1" *--> "collegeUseagesMedicines *" CollegeUseageMedicine 
Inventory "1" *--> "orderMedicine 1" OrderMedicine 
Medicine "1" *--> "medicineCategory 1" MedicineCategory 
Medicine "1" *--> "orderMedicines *" OrderMedicine 
Medicine "1" *--> "useageMedicines *" UseageMedicine 
Notification "1" *--> "medicine 1" Medicine 
Order "1" *--> "orderMedicines *" OrderMedicine 
Order "1" *--> "supplier 1" Supplier 
OrderMedicine "1" *--> "medicine 1" Medicine 
OrderMedicine "1" *--> "order 1" Order 
Patient "1" *--> "disease *" Disease 
Prescription "1" *--> "medicines *" Medicine 
Prescription "1" *--> "patient 1" Patient 
Prescription "1" *--> "prsPrescriptionCategory 1" PrescriptionCategory 
Useage "1" *--> "prescription 1" Prescription 
Useage "1" *--> "useageMedicines *" UseageMedicine 
UseageMedicine "1" *--> "inventory 1" Inventory 
UseageMedicine "1" *--> "medicine 1" Medicine 
UseageMedicine "1" *--> "useage 1" Useage 
User "1" *--> "authority *" Authority 

```
## Controllers Readme

This repository contains several controllers implemented in a Spring Boot application. Each controller handles specific HTTP endpoints and interacts with corresponding services. Below is a brief description of each controller and its associated endpoints:

### DashboardController

- **RequestMapping**: `/dashboard`
- **Description**: Provides endpoints related to the dashboard.
- **Endpoints**:
    - `GET /status`: Retrieves the status of medicines.

### DiseaseController

- **RequestMapping**: `/diseases`
- **Description**: Handles operations related to diseases.
- **Endpoints**:
    - `GET`: Retrieves all diseases.
    - `POST`: Adds a new disease.
    - `PUT`: Updates an existing disease.

### MedicineCategoryController

- **RequestMapping**: `/medicinecategories`
- **Description**: Handles operations related to medicine categories.
- **Endpoints**:
    - `GET`: Retrieves all medicine categories.
    - `GET /{id}`: Retrieves a specific medicine category by ID.
    - `POST`: Adds a new medicine category.
    - `DELETE /{id}`: Deletes a medicine category.
    - `PUT`: Updates an existing medicine category.

### MedicineController

- **RequestMapping**: `/medicines`
- **Description**: Handles operations related to medicines.
- **Endpoints**:
    - `GET`: Retrieves all medicines.
    - `GET /{id}`: Retrieves a specific medicine by ID.
    - `POST`: Adds a new medicine.
    - `DELETE /{id}`: Deletes a medicine.
    - `PUT`: Updates an existing medicine.
    - `GET /category/{id}`: Retrieves all medicines belonging to a specific category.

### OrderController

- **RequestMapping**: `/orders`
- **Description**: Handles operations related to orders.
- **Endpoints**:
    - `GET`: Retrieves all orders.
    - `GET /{id}`: Retrieves a specific order by ID.
    - `POST`: Adds a new order.
    - `DELETE /{id}`: Deletes an order.
    - `PUT`: Updates an existing order.
    - `GET /supplier/{id}`: Retrieves all orders from a specific supplier.

### PatientController

- **RequestMapping**: `/patients`
- **Description**: Handles operations related to patients.
- **Endpoints**:
    - `GET`: Retrieves all patients.
    - `POST`: Adds a new patient.
    - `GET /{id}`: Retrieves a specific patient by ID.
    - `PUT`: Updates an existing patient.
    - `DELETE /{id}`: Deletes a patient.

### PrescriptionCategoryController

- **RequestMapping**: `/prescriptioncategories`
- **Description**: Handles operations related to prescription categories.
- **Endpoints**:
    - `GET`: Retrieves all prescription categories.
    - `POST`: Adds a new prescription category.
    - `PUT`: Updates an existing prescription category.
    - `DELETE /{id}`: Deletes a prescription category.

### PrescriptionController

- **RequestMapping**: `/prescriptions`
- **Description**: Handles operations related to prescriptions.
- **Endpoints**:
    - `GET`: Retrieves all prescriptions.
    - `GET /{id}`: Retrieves a specific prescription by ID.
    - `POST`: Adds a new prescription.
    - `PUT`: Updates an existing prescription.
    - `DELETE /{id}`: Deletes a prescription.

### SupplierController

- **RequestMapping**: `/suppliers`
- **Description**: Handles operations related to suppliers.
- **Endpoints**:
    - `GET`: Retrieves all suppliers.
    - `GET /{id}`: Retrieves a specific supplier by ID.
    - `POST`: Adds a new supplier.
    - `DELETE /{id}`: Deletes a supplier.
    - `PUT`: Updates an existing supplier.

### UseageController

- **RequestMapping**: `/useages`
- **Description**: Handles operations related to usages.
- **Endpoints**:
    - `GET`: Retrieves all usages.
    - `GET /{id}`: Retrieves a specific usage by ID.
    - `POST`: Adds a new usage.
    - `PUT`: Updates an existing usage.
    - `DELETE /{id}`: Deletes a usage.

Feel free to explore and utilize these controllers according to your application's requirements.