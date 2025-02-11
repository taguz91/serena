import { Chart as ChartJS } from 'chart.js'

let loadedChartConfig = false

export const loadChartConfig = () => {
  if (ChartJS.overrides['pie'] && !loadedChartConfig) {
    loadedChartConfig = true
    ChartJS.overrides['pie'].plugins.legend = {
      ...ChartJS.overrides['pie'].plugins.legend,
      labels: {
        ...ChartJS.overrides['pie'].plugins.legend.labels,
        boxHeight: 38,
        font: {
          ...ChartJS.defaults.font,
          size: 16
        }
      },
      title: {
        display: true,
        color: '#000',
        font: {
          ...ChartJS.defaults.font,
          size: 20,
          weight: 'bold'
        },
        text: 'Estados',
        position: 'start',
        padding: 10
      },
      position: 'right',
      align: 'center'
    }

    // promise wait for 2 seconds
    return new Promise<void>((resolve) => {
      setTimeout(() => {
        resolve()
      }, 1500)
    })
  }
}

export const pieOptions = {
  responsive: true,
  maintainAspectRatio: false,
  position: 'right'
}
