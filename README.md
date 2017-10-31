#Program to extract median, total dollar amount and total number of contributions by recipient and zip code/date.
 
**Description**
```
Program takes the Federal Election Commission data file that contains campaign contribution as input file and emits two files:

    1. medianvals_by_zip.txt: contains running median of contributions, total number of transactions and total amount 
		of contributions for each recipient and zip code.

    2. medianvals_by_date.txt: every unique combination of date and recipient and the calculated total contributions 
		and median contribution for that combination.

```

**Input file format**
```
The input file will conform to the data dictionary as described by the FEC 
(http://classic.fec.gov/finance/disclosure/metadata/DataDictionaryContributionsbyIndividuals.shtml)

Example:
C00177436|N|M2|P|201702039042410894|15|IND|DEEHAN, WILLIAM N|ALPHARETTA|GA|300047357|UNUM|SVP, SALES, CL|01312017|384||PR2283873845050|1147350||P/R DEDUCTION ($192.00 BI-WEEKLY)|4020820171370029337

sample input file "itcont.txt" is present in "input" folder
```

**Output file format**
```
Two output files are created in the "output" folder with the names "medianvals_by_zip.txt" and "medianvals_by_date.txt"
```
**Compiling and running Program**
```
Just run the file "run.sh" or the following commands:

javac ./src/*.java
java -cp ./src FindPoliticalDonors ./input/itcont.txt ./output/medianvals_by_zip.txt ./output/medianvals_by_date.txt
```