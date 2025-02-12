<template>
  <div class="bg-white rounded-xl min-h-16 mb-4 p-4 relative">
    <h1 class="text-xl font-bold">{{ title }}</h1>
    <hr class="my-2" />

    <slot />
  </div>
</template>

<script setup lang="ts">
defineProps<{
  title: string
}>()
</script>
