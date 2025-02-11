<template>
  <NLoadingBarProvider>
    <NMessageProvider>
      <NConfigProvider :locale="esAR" :date-locale="dateEnUS">
        <WrapperView />
      </NConfigProvider>
    </NMessageProvider>
  </NLoadingBarProvider>
</template>

<script setup lang="ts">
import { dateEnUS, esAR, NConfigProvider, NLoadingBarProvider, NMessageProvider } from 'naive-ui'
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'

import WrapperView from './views/WrapperView.vue'
import { loadChartConfig } from './helpers/loadChartConfig'

ChartJS.register(ArcElement, Tooltip, Legend)

loadChartConfig()
</script>
