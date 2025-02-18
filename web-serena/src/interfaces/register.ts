import type { Classroom } from './classrooms'

export type StatusRegister = 'open' | 'closed' | 'cancelled' | 'inscription' | 'expired'

export interface Register {
  createdAt: string
  updatedAt: string
  createdBy: string
  updatedBy?: string
  id: string
  date: string
  topic: string
  status: StatusRegister
  classroom: Classroom
}

export interface RegisterForm {
  id?: string
  status: StatusRegister
  idClassroom: string
  topic: string
}

export interface RegisterStudentForm {
  photo: string
  idRegister: string
}

export interface RegisterStudentDuplicateForm {
  photo?: string
  idRegister: string
  idStudent: string
}
