<template>
  <NLoadingBarProvider>
    <NMessageProvider>
      <WrapperView />
    </NMessageProvider>
  </NLoadingBarProvider>
</template>

<script setup lang="ts">
import { NLoadingBarProvider, NMessageProvider } from 'naive-ui'
import WrapperView from './views/WrapperView.vue'
</script>
