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
import WrapperView from './views/WrapperView.vue'
</script>
