# File Content Filter Utility

## Description
A console utility that filters files' (both single and multiple files input configurations are supported) contents into three separate categories based on their data type (integers, decimals, strings). Each data type is then sorted into a corresponding output file. The process statistics are optionally collected and subsequently displayed in the console.

## Requirements
- **[Java 25](https://www.oracle.com/java/technologies/downloads/#java25) (Oracle Java SE Development Kit)**
- **Gradle 9.2.0**

## Dependencies
- **[Apache Commons CLI 1.11](https://commons.apache.org/proper/commons-cli/)** - for command line argument parsing
- All dependencies are automatically managed through Gradle

## Building
```bash
# Build JAR file
./gradlew jar

# Clean project
./gradlew clean
```
JAR file will be located at `build/libs/`

## Usage
```bash
java -jar filefilter.jar [-s/f] [-a] [-o <PATH>] [-p <PREFIX>] <FILES>
```

### Options
- `-?/--help` - Display help message
- `-s/--short` - Display short statistics (amount only) **OR**
- `-f/--full` - Display full statistics (min/max/sum/avg)
- `-a/--append` - Append mode (default: overwrite)
- `-o/--output <PATH>` - Output directory path (default: current directory)
- `-p/--prefix <PREFIX>` - Prefix for output files (default: none)

### Examples
1. Basic example:
```bash
java -jar filefilter.jar example.txt
```
2. With custom output path and prefix:
```bash
java -jar filefilter.jar -o /some/path -p result_ example.txt
```
3. Append mode with full statistics (and multiple inputs):
```bash
java -jar filefilter.jar --full --append in1.txt in2.txt
```
4. Display help:
```bash
java -jar filefilter.jar --help
```

## Implementation
- Files are accessed in order specified within the command line argument
- Files' content is read and parsed line-by-line
- Empty lines are ignored
- Parsing is only supported for the following types:
  - `BigInteger`
  - `BigDecimal`
  - `Strings` (for all the other types)
- Output files are overwritten by default (use `-a/--append` option if needed)
- `BigDecimal` scale is `4` and `HALF_UP` rounding mode is applied

## Notes
- With the `-a/--append` option, files are either created or appended based on file existence and data availability 
- The prefix `-p/--prefix` applies to all output files
- Output files are created only when there's data of that type (no empty files)
- The program handles various number formats including scientific notation
