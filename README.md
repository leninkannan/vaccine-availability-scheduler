###### **Vaccine Slots Availability Scheduler**

It provides the vaccine slots availability by every 10 seconds for the given postcodes, for given age-limit(18 or 45) and for given dose (1 or 2).

Beep sounds will be generated if slots are available.

*Follow below steps to run the program*


**Step:1 - Install Java 8 Application**

https://www.java.com/en/download/help/windows_manual_download.html

**Step:2 - Run the below command by replacing the Pincodes to check the availability of the slots**

_For age 18+ and Dose1_

`java -Dmin-age-limit=18 -Ddose=1 -Dpincodes=600095,600096 -jar vaccine-availability-scheduler-0.0.2.jar
`

_For age 18+ and Dose2_

`java -Dmin-age-limit=18 -Ddose=2 -Dpincodes=600095,600096 -jar vaccine-availability-scheduler-0.0.2.jar
`

_For age 45+  and Dose1_

`java -Dmin-age-limit=45 -Ddose=1 -Dpincodes=600095,600096 -jar vaccine-availability-scheduler-0.0.2.jar
`

_For age 45+ and Dose2_

`java -Dmin-age-limit=45 -Ddose=2 -Dpincodes=600095,600096 -jar vaccine-availability-scheduler-0.0.2.jar
`
