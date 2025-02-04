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

export const emotionLabel = (emotion: string) => {
  return EMOTIONS[emotion] ?? emotion
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
