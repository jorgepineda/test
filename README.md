Instructions
------------
Download the project from GitHub repository : https://github/jorgepineda/test.git

Services are available on port 8083 after executing the class com.landbay.loans.LoanApplication.

The API uses an "in-memory" database H2 that is flushed every time the api is shutdown. There is no need to define another database to run.

The services offered by the API are:

1. http://localhost:8083/loans/create-loan/{debtor}/{amount}/{interest}

Creates a loan in the database for a given debtor, an amount and an interest rate (0 to 100).
Example:
	http://localhost:8083/loans/create-loan/John/1000/5

Retrieves an integer identifying the loan or LoanId.

2. http://localhost:8083/loans/create-investment/{investor}/{amount}

Creates an investment in the database for a given investor with an amount to invest.
Example
http://localhost:8083/loans/create-investment/Barclays/10000

Retrieves an integer identifying the investment or InvestmentId

3. http://localhost:8083/loans/add-lending/{loanID}/{investmentId}/{amountToInvest/{effectiveDate}

Adds an investment to a loan. A loan can have many investments and an investment can be split into many loans
(many to many relationship). Before the association is made, the service checks:
a) Both the loan and investment already exists in the database.
b) The investment has enough money to invest the given amount.
c) The loan requires that amount or more.
The operation diminishes the investor capacity and increments the money already lended to the Loan.

Example
http://localhost:8083/loans/add-lending/1/1/500/2015-03-01

if an exception is not retrieved, the operation is successful.

4. http://localhost:8083/loans/loan/{loanID}

Retrieves a loan give its Id:

Example:
http://localhost:8083/loans/loan/1

5. http://localhost:8083/investments/loan/{loanId}

Retrieves the list of active investments for a given a loanId
Example:
 http://localhost:8083/investments/loan/1

6. http://localhost:8083/loans/pay/{loanId}/{investmentId}/{amount}

Pays the given amount into the investment debt. This operation verifies:
a) Both the loan and investment exist and are associated.
b) The amount is not greater than the debt.

The operation augments the investment capacity and reduces the loan debt. When the debt is reduced to zero, the association
between the investment and the loan disappears.

Example:
 http://localhost:8083/loans/pay/1/1/300

if an exception is not retrieved, the operation is successful.

7. http://localhost:8083/loans/interests-report/{startDate}/{endDate}

Retrieves a report of the investments with the detail of interests caused by each associated loan.

The report is a String separated by "end of line" characters.

Example:
http://localhost:8083/loans/interests-report/2015-01-01/2015-12-31

8. http://localhost:8083/loans/delete-investment/{investmentId}

Deletes the investment with the given Id provided that the investment exists and is not associated to any loan. Otherwise an exception is thrown.

Example:
http://localhost:8083/loans/delete-investment/1

9. http://localhost:8083/loans/delete-loan/{loanId}

Deletes the loan with the given Id provided that the loan exists and is not associated to any investment. Otherwise an exception is thrown.

Example:
http://localhost:8083/loans/delete-loan/1