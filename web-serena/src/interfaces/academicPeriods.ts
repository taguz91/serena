export interface AcademicPeriod {
  createdAt: string
  updatedAt: string
  createdBy: string
  updatedBy?: string
  id: string
  name: string
  reference?: string
  isActive: boolean
  classrooms: unknown[]
}

export interface AcademicPeriodForm {
  id?: string
  name: string
  isActive: boolean
}
