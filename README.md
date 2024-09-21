
---

# Fedha Youth Group System

## Overview

The **Fedha Youth Group System** is a computerized information system developed as an assessment project for students taking SCO200 – Object Oriented Programming II in semester I 2024/2025 at **Kenyatta University**. It is designed to facilitate the operations of the Fedha Youth Group, a hypothetical organization dedicated to empowering youths aged 18 to 35 by providing savings and loan services.

### Purpose

This system aims to streamline the management of member accounts, loan processing, and financial operations, making it easier for members to manage their contributions and access loans. The project's scope has been extended to demonstrate a basic **Core Banking System (CBS)** and  **Enterprise Resource Planning (ERP)** functionality.

## Features

### Membership Management
- Register and manage members, including their registration fees and share contributions.

### Loan Management
- Maintain records of loan types, loans borrowed by members, and the corresponding guarantors.

### Financial Calculations
- Compute:
    - Total registration fees
    - Total shares
    - Maximum loan eligibility
    - Interest on loans and fixed deposits
    - Monthly loan repayments
    - Dividends payable to members
    - Guaranteed amounts for loans
    - Office expenses retained by the organization

### Reporting
- Generate detailed reports, including:
    - Member registration fees
    - Share contributions
    - Fixed deposits
    - Loans borrowed
    - Loan repayment schedules
    - Balances after repayments
    - Dividends payable to members

## Technical Details

- **Language**: Java
- **Database**: MySQL (using custom utility classes for database operations)
- **Design Principles**: Object-Oriented Programming, Double-Entry Bookkeeping principles, Modular design

### Installation

1. Clone the repository to your local machine.
2. Ensure you have Java and a compatible database setup.
3. Update the `config.xml` file with your configurations.
4. Compile and run the main application class.

### Usage

Once the application is running, users can perform various operations via the user interface, and automated tasks for updating account balances and handling dividends are scheduled to run periodically.

## Conclusion

This project is a stepping stone towards understanding the complexities of financial systems and demonstrates how technology can enhance the management of Savings and Credit Cooperative Organizations. Your contributions and feedback are welcome!

---