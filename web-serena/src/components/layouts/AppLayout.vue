<template>
  <header class="bg-blue-300 py-5 px-10 flex justify-between border-b-2">
    <div>
      <h1 class="font-bold text-2xl">Serena</h1>
    </div>

    <div>
      <NButton tertiary circle type="error">
        <template #icon>
          <NIcon><Logout /></NIcon>
        </template>
      </NButton>
    </div>
  </header>

  <main class="bg-slate-100 min-h-screen w-screen flex">
    <div class="w-full">
      <slot />
    </div>

    <div class="w-2/5 border-l-2">
      <slot name="side" />
    </div>
  </main>
</template>

<script setup lang="ts">
import { NButton, NIcon } from 'naive-ui'

import Logout from '@vicons/tabler/Logout'
</script>
