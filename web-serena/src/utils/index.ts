export * from './params'

export const delay = (ms: number) => new Promise((res) => setTimeout(res, ms))
