# Serena

Amazon Recoknition to monitor the feeling of students, before and after classes

For UI design we use: https://www.naiveui.com/en-US/light

Entities

- Academic Period (Only one can be active)
  - name
  - reference
  - is_active
- Students records
  - identification
  - reference
  - name
  - gender
  - photo
- Class records
  - student_reference
  - teacher_reference
- Teachers
  - reference
  - name
  - email
  - password
  - token
- Students
  - identification
  - gender
  - name
- Subjects
  - name
- Classroom
  - Academic Period
  - Teacher
  - Subject
- Register
  - date
  - status
  - Classroom
- Register Student
  - Student
  - photo
  - emotion
- Class summary
  - date
  - Classroom
  - avg_emotion
  - min_emotion
  - max_emotion
- Student report
  - date
  - Classroom
  - Student
  - start_emotion
  - end_emotion
- Class report
  - date
  - start_emotion
  - end_emotion
  - Register
  - Student
