export interface RegisterStudent {
  createdAt: string
  updatedAt: string
  createdBy: string
  updatedBy: any
  id: string
  photo: string
  emotion: string
  student: Student
}

export interface Student {
  createdAt: string
  updatedAt: string
  createdBy: string
  updatedBy: any
  id: string
  identification: string
  name: string
  gender: string
  reference: any
}

export interface StudentForm {
  id?: string
  identification: string
  name: string
  lastname: string
  gender: string
}

export interface StudentSubject {
  id: string
  name: string
}
