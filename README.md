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
- Inscription
  - Classroom
  - students
  - photos
  - photo
- Register
  - date
  - status
  - Classroom
- Register Student
  - Register
  - Student
  - photo
  - emotion
- Student Emotion
  - RegisterStudent
  - emotion
  - percentage
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
  - start_emotion
  - end_emotion
  - Register
  - Student
- SyncHistory
  - type
  - filename
  - status
  - s3key

### Build api application

```bash
./mvnw package && java -jar target/gs-spring-boot-docker-0.1.0.jar
```
