# ALD-style
Firstly, it should be noted that all the code is documented, and this documentation can be found by opening <b>resources/doc/index.html</b> in your browser. Two important things should be highlighted before running the practice.

Our code connects through a REST API to the GMAIL email service, however, a password is required which is not directly attached within the application.properties file for security reasons. To do this, you need to pass the following argument <b>"-Dspring.mail.password=bmtsjpxnnxfafmpu"</b> to the Java VM before running the application. In the case of IntelliJ, this option can be found in <b>Run > Edit Configurations > Modify Options > Select the hidden option of VM Options</b>, and a box will appear where you can include the previously provided argument.

Another important thing is that the POSTMAN collection is exported within a JSON file in test/resources, so it would just need to be imported into POSTMAN and run. It is important to note that things such as user registration can only be executed once since our website does not allow duplicate users. Therefore, if you want to test it more than once, the body must be modified. Some controllers are not included; they have been added only as examples. Additionally, all POSTMAN requests are based solely on error codes that the backend handles when communicating with the frontend.
