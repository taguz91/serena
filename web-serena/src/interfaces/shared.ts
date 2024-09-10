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
