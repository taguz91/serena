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

export type OptionType = 'academic-period' | 'subject' | 'teacher' | 'classroom'

export interface Option {
  label: string
  value: string
}
