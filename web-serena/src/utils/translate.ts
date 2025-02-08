const EMOTIONS: Record<string, string> = {
  HAPPY: 'Feliz',
  SAD: 'Triste',
  ANGRY: 'Enojado',
  CONFUSED: 'Confundido',
  DISGUSTED: 'Disgustado',
  SURPRISED: 'Sorprendido',
  CALM: 'Calmando',
  UNKNOWN: 'Neutral',
  FEAR: 'Asustado'
}

const EMOTIONS_COLORS: Record<string, string> = {
  HAPPY: '#FFFFCC', // Lighter Yellow
  SAD: '#CCCCFF', // Lighter Blue
  ANGRY: '#FFCCCC', // Lighter Red
  CONFUSED: '#E6CCE6', // Lighter Purple
  DISGUSTED: '#CCFFCC', // Lighter Green
  SURPRISED: '#FFE6CC', // Lighter Orange
  CALM: '#CCFFFF', // Lighter Cyan
  UNKNOWN: '#E0E0E0', // Lighter Gray
  FEAR: '#D9B3B3' // Lighter Maroon
}

export const emotionLabel = (emotion: string) => {
  return EMOTIONS[emotion] ?? emotion
}

export const emotionColor = (emotion: string) => {
  return EMOTIONS_COLORS[emotion] ?? '#000000'
}

const STATUS: Record<string, string> = {
  pending: 'Pendiente',
  expired: 'Expirado',
  active: 'Activo',
  open: 'Activo'
}

export const statusLabel = (status: string) => {
  return STATUS[status] ?? status
}
