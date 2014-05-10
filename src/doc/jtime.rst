=====
jtime
=====

---------------------------
Textfile based time tracker 
---------------------------

:Author: Jonny Heggheim <hegjon@gmail.com>
:Date: 2014-03-28
:Copyright: ASL 2.0
:Version: 1.0
:Manual section: 1

SYNOPSIS
========
  jtime [command] [options]

DESCRIPTION
===========
jtime will calculate ....

COMMANDS
========
print
  Prints a weekly summery, this is the default command

total-diff
  Prints the total diff

vacations
  Prints all vacations dates

OPTIONS
=======
--file <file>, -f <file>     Read the time entries from <file>. Will fallback to **JTIME_DEFAULT_FILE** environment variable if no argument is given.
--help, -h                   Shows help message and exit

FILEFORMAT
==========
jtime fileformat is based on YAML. Top level keys:

year
  Required. The current year for the document.

default
  Optional. The number of hours and minutes per weekday.

january
  Optional.

february, march, april, may, june, july, august, september, november, december
  Optional.

EXAMPLE
=======

.. include:: example.time.yaml
   :code: none
   :literal:
