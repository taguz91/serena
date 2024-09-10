export interface Subject {
  createdAt: string
  updatedAt: string
  createdBy: string
  updatedBy?: string
  id: string
  name: string
  classrooms: unknown[]
}

export interface SubjectForm {
  id?: string
  name: string
}
