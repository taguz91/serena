import type { Career } from './careers'

export interface AcademicPeriod {
  createdAt: string
  updatedAt: string
  createdBy: string
  updatedBy?: string
  id: string
  name: string
  reference?: string
  isActive: boolean
  carrera?: Career
}

export interface AcademicPeriodForm {
  id?: string
  name: string
  idCarrera: string
  isActive: boolean
}
