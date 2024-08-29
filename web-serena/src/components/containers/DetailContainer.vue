<template>
  <div class="py-10 px-14">
    <NBreadcrumb class="mb-4">
      <NBreadcrumbItem @click="goTo('app-home')"> Inicio </NBreadcrumbItem>

      <NBreadcrumbItem> {{ current }} </NBreadcrumbItem>
    </NBreadcrumb>

    <slot />
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

import { NBreadcrumb, NBreadcrumbItem } from 'naive-ui'

const router = useRouter()

defineProps<{
  current: string
}>()

const goTo = (to: string) => {
  router.push({
    name: to
  })
}
</script>
