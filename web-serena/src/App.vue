<template>
  <NMessageProvider>
    <template v-if="isAdmin">
      <AdminLayout />
    </template>

    <RouterView v-else />
  </NMessageProvider>
</template>

<script setup lang="ts">
import { RouterView, useRoute } from 'vue-router'

import { NMessageProvider } from 'naive-ui'
import { computed } from 'vue'
import AdminLayout from './components/layouts/AdminLayout.vue'

const route = useRoute()

const isAdmin = computed(() => route.path.startsWith('/admin'))
</script>
