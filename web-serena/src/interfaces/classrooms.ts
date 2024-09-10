import type { AcademicPeriod } from './academicPeriods'
import type { Subject } from './subject'
import type { Teacher } from './teacher'

export interface Classroom {
  createdAt: string
  updatedAt: string
  createdBy: string
  updatedBy?: string
  id: string
  academicPeriod: AcademicPeriod
  teacher: Teacher
  subject: Subject
}

export interface ClassroomForm {
  id?: string
  idAcademicPeriod: string
  idTeacher: string
  idSubject: string
}
