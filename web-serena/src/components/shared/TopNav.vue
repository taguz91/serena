<template>
  <header class="bg-blue-300 py-5 px-10 flex justify-between border-b-2">
    <div>
      <h1 class="font-bold text-2xl">{{ title }}</h1>
    </div>

    <div>
      <NButton tertiary circle type="error">
        <template #icon>
          <NIcon><Logout /></NIcon>
        </template>
      </NButton>
    </div>
  </header>
</template>

<script setup lang="ts">
import { Logout } from '@vicons/tabler'
import { NButton, NIcon } from 'naive-ui'

defineProps<{
  title: string
}>()
</script>
