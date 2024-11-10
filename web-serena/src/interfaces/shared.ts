export interface Paginate<T> {
  data: T[]
  meta: Meta
}

export interface Meta {
  current: number
  items: number
  pages: number
  perPage: number
}

export type OptionType = 'academic-period' | 'subject' | 'teacher' | 'classroom' | 'carrera'

export interface Option {
  label: string
  value: string
}

export interface Summary {
  emotion: string
  count: number
}

export interface SessionInfo {
  academicPeriods: string[]
}
