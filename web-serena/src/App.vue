<template>
  <NMessageProvider>
    <RouterView />
  </NMessageProvider>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'

import { NMessageProvider } from 'naive-ui'
</script>
