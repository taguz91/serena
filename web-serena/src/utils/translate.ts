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
