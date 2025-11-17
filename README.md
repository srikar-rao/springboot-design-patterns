# Spring Boot Design Patterns

This project demonstrates the implementation of various design patterns in a Spring Boot application. The project showcases how design patterns can be used to create maintainable, extensible, and flexible software architecture.

## Export Feature Overview

### Purpose
The Export feature demonstrates multiple design patterns working together in one cohesive workflow. It provides a flexible and extensible mechanism for exporting project-related data in different formats (CSV, Excel) with optional enhancements like compression. The feature showcases how design patterns can be combined to create a robust and maintainable solution for a common business requirement.

### Design Patterns Demonstrated

#### Template Method Pattern
Implemented through `AbstractExporter` and its concrete implementations (`ProjectExporter`, `UserExporter`, `TaskExporter`). This pattern defines the skeleton of the export algorithm in the abstract class while allowing subclasses to override specific steps without changing the algorithm's structure.

#### Factory Pattern
Implemented through `ExporterFactory`, which centralizes the creation and selection of appropriate exporter instances based on the requested export type. This pattern encapsulates the instantiation logic and provides a clean interface for obtaining the right exporter.

#### Strategy Pattern
Implemented through `FileGeneratorStrategy` and its concrete implementations (`CsvFileGenerator`, `ExcelFileGenerator`). This pattern enables the selection of different file generation algorithms at runtime, allowing the system to switch between CSV and Excel formats seamlessly.

#### Decorator Pattern
Implemented through `FileGeneratorDecorator` and its concrete implementation (`CompressionDecorator`). This pattern allows for dynamic enhancement of file generators with additional functionality like compression, without modifying their core behavior.

### Architecture Summary

The export feature follows a layered architecture with clear separation of concerns:

1. **Controller Layer** (`ExportController`): Handles HTTP requests and delegates to the service layer.
2. **Service Layer** (`ExportService`): Orchestrates the workflow by:
   - Using the factory to get the appropriate exporter
   - Retrieving data through the template method pattern
   - Selecting the appropriate file generation strategy
   - Optionally applying decorators for enhancements
3. **Factory Layer** (`ExporterFactory`): Provides the correct exporter implementation.
4. **Template Layer** (`AbstractExporter` and subclasses): Defines the data retrieval workflow.
5. **Strategy Layer** (`FileGeneratorStrategy` implementations): Handles file format generation.
6. **Decorator Layer** (`FileGeneratorDecorator` and subclasses): Provides optional enhancements.

### Extensibility Notes

The export feature is designed for easy extension:

- **Adding New Exporters**: Create a new class extending `AbstractExporter` and implement the required methods. The factory will automatically pick it up through dependency injection.
- **Supporting New File Formats**: Implement the `FileGeneratorStrategy` interface for the new format. No changes to existing code required.
- **Adding New Enhancements**: Create new decorators extending `FileGeneratorDecorator` to add functionality like encryption, auditing, or watermarking.

### Future Work

- **Bridge Pattern**: Separate export logic from output channels (file system, S3, stream).
- **Async Processing**: Implement asynchronous export processing for large datasets using Spring events or Kafka.
- **Dynamic Column Filtering**: Allow users to specify which columns to include in exports.
- **Additional Decorators**: Implement encryption and auditing decorators.

--- 
*End of Export Feature Documentation*

## Discount Engine Overview

### Purpose
The Discount Engine feature demonstrates how multiple design patterns—Composite, Chain of Responsibility, Specification, Rule, and Null Object—can be combined to create a flexible and extensible pricing system. It calculates the final price of a book by applying various discounts based on a selected strategy (`COMPOSITE`, `CHAINED`, etc.), showcasing a clean separation of concerns and high cohesion.

### Design Patterns Demonstrated

- **Composite Pattern**: Implemented with `DiscountComponent` and `CompositeDiscount` to group multiple discounts and treat them as a single unit.
- **Chain of Responsibility Pattern**: Implemented with `DiscountHandler` and `ChainedDiscountProcessor` to apply discounts sequentially, allowing each handler in the chain to process the request.
- **Specification Pattern**: Implemented with `DiscountSpecification` and its concrete implementations (`StartsWithCSpecification`, `BulkPurchaseSpecification`) to define complex business rules for discount eligibility in a composable way.
- **Rule Pattern**: Implemented with `DiscountRule` to encapsulate the specific logic for each discount, making the system easy to extend with new rules.
- **Null Object Pattern**: Implemented with `NoDiscountRule` to provide a safe, default "do-nothing" behavior, eliminating the need for null checks when no discount is applicable.

### Architecture Summary

The feature's architecture is designed around composition and clear separation of responsibilities:

1.  **Controller Layer** (`DiscountController`): Exposes the `/api/book/price` endpoint to receive pricing requests.
2.  **Service Layer** (`DiscountService`): Orchestrates the discount calculation. It selects the appropriate strategy (e.g., Composite, Chained) and assembles the necessary discount components, rules, and specifications to compute the final price.
3.  **Pattern Components**: A collection of interfaces and classes that implement the core design patterns, providing the building blocks for the discount logic.
4.  **Models** (`Book`, `DiscountResponse`): Define the data structures for API requests and responses.

### Example Request/Response

**Request:**
```json
POST /api/book/price
{
  "bookName": "Clean Code",
  "basePrice": 50.00,
  "discountMode": "COMPOSITE"
}
```

**Response:**
```json
{
  "bookName": "Clean Code",
  "originalPrice": 50.00,
  "finalPrice": 35.00,
  "discountsApplied": [
    {"discountType": "NameStartsWithCDiscount", "amount": 5.00, "description": "Book name starts with 'C'"},
    {"discountType": "HolidayDiscount", "amount": 10.00, "description": "Seasonal offer applied"}
  ],
  "notes": [
    "COMPOSITE discount mode applied",
    "Final price validated not below $0"
  ]
}
```

---
*End of Discount Engine Documentation*
