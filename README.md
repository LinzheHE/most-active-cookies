# most-active-cookies
## Assumption
- assume users may use the application for different cookie_log files
- assume the files' content are in the correct format (one column for cookie, one column for timestamp)

## Environment
- The project is built using java and gradle.

## Execution
1. clone the repo to you local machine
2. cd to `repo_path/most-active-cookie/src/main/java`
  - please make sure to replace the `repo_path` with actual path
  - when you run `ls` in the terminal, you're expected to see App.class (compiled file for the App.java)
3. Run `java App input_file_path/cookie_log.csv -d 2018-12-09`
  - please make sure to replace the `input_file_path` with actual path
  - sample file: `repo_path/most-active-cookie/src/main/resources/cookie_log.csv`
  - you could replace the `cookie_log.csv` with other files you want to query from
  - you could replace the `2018-12-09` with other dates you want to query

## Test Coverage
1. The project achieved 100% test coverage rate.
