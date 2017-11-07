- LoginSystem is now the main class that is used to access the entirety of the ITS.
    - Remember me and created users (other than default resource users), are saved in a folder created in the user's home directory under 'JavaTutorDeluxe'
    - Default username: admin
    - Default password: password
    - Default rights for the admin user is INSTRUCTOR
    - Every created user will be considered a STUDENT

-Help option on menu launches JavaMail integrated interface
    - send functionality can be observed in the testrecipient gmail
    - username: testrecipient.cse360@gmail.com
    - password: testrecipient
    - the necessary dependencies are loaded through maven's dependency system (see pom.xml)

-Empathetic Tutor
  - edited the AnimatedCompanion class and added a variable that has the total number of correct answers and incorrect answers.
  - Then using this, I edit the graphics2D.DrawImage to draw a different image depending on the number of correct and incorrect answers
  - Also edited the MoveImage() method so that the image goes in a circle if the total number of correct answers is less than the total number of incorrect answers
  -Decorator pattern implemented. This adds text to the Companion panel based on student performance
  
-Instructor's Classroom can be launched through the Classroom option in the menu
  - Will only be accessible to instructors
  - List of premade assignments are loaded from a folder under resources
    - Instructor will be able to change assignment deadline, name, total points
    - Instructor wil be able to sort the assignments based on name
  - List of questions that will be used for assessments will be loaded from a text file under resources and displayed 

-Instructor's Assignments can be launched through the Load option in the menu bar
    - Questions will be chosen randomly from a file under resources
    - Instructor will be able to see and answer the questions
    - Score or time will not be counted
