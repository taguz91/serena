export const ecuadorianIdentification = (numero: string | undefined) => {
  if (numero === undefined) return false

  if (numero.length > 10) return false

  const d3 = parseInt(numero.substring(2, 3))

  if (d3 === 7 || d3 === 8) return false
  const pernat = '212121212'
  let suma = 0

  for (let i = 0; i < 9; i++) {
    const n = parseInt(numero.substring(i, i + 1))
    const m = parseInt(pernat.substring(i, i + 1))
    let a = n * m

    if (a > 9) a = a - 9
    suma = suma + a
  }
  const modulo = 10
  const residuo = suma % modulo
  const digitoVerificador = residuo === 0 ? 0 : modulo - residuo

  if (digitoVerificador !== parseInt(numero.substring(9, 10))) return false

  return true
}
