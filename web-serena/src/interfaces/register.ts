import type { Classroom } from './classrooms'

export type StatusRegister = 'open' | 'closed' | 'cancelled'

export interface Register {
  createdAt: string
  updatedAt: string
  createdBy: string
  updatedBy?: string
  id: string
  date: string
  status: StatusRegister
  classroom: Classroom
}

export interface RegisterForm {
  id?: string
  status: StatusRegister
  idClassroom: string
}

export interface RegisterStudentForm {
  photo: string
  idRegister: string
}
