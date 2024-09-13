export * from './params'

export const delay = (ms: number) => new Promise((res) => setTimeout(res, ms))

export const dataURLtoFile = (data: string, filename: string) => {
  const arr = data.split(',')
  if (!arr[0]) {
    return
  }

  const mime = arr[0].match(/:(.*?);/)![1]

  const bstr = atob(arr[arr.length - 1])
  let n = bstr.length
  const u8arr = new Uint8Array(n)

  while (n--) {
    u8arr[n] = bstr.charCodeAt(n)
  }

  return new File([u8arr], filename, { type: mime })
}

export const dataURIToBlob = (dataURI: string) => {
  const splitDataURI = dataURI.split(',')
  const byteString =
    splitDataURI[0].indexOf('base64') >= 0 ? atob(splitDataURI[1]) : decodeURI(splitDataURI[1])
  const mimeString = splitDataURI[0].split(':')[1].split(';')[0]

  const ia = new Uint8Array(byteString.length)
  for (let i = 0; i < byteString.length; i++) ia[i] = byteString.charCodeAt(i)

  return new Blob([ia], { type: mimeString })
}

export const dataURItoBlob = (dataURL: string) => {
  const blobBin = atob(dataURL.split(',')[1])
  const array = []
  for (let i = 0; i < blobBin.length; i++) {
    array.push(blobBin.charCodeAt(i))
  }
  return new Blob([new Uint8Array(array)], { type: 'image/png' })
}
