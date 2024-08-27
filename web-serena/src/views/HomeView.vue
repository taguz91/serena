<template>
  <SinglePageLayout>
    <div class="flex flex-col items-center justify-center h-3/5 m-auto w-full">
      <div class="flex items-center gap-x-2">
        <Icon>
          <Social class="h-10 w-10" />
        </Icon>
        <p class="font-semibold text-2xl">Serena</p>
      </div>

      <div class="h-full w-[800px] mt-6 rounded-lg flex">
        <LoginForm />

        <div
          class="bg-blue-100 opacity-40 w-3/5 h-full p-8 flex flex-col items-center justify-center"
        >
          <p class="text-center text-lg">
            "La educación es el arma más poderosa que puedes usar para cambiar el mundo."
          </p>

          <p class="text-center text-slate-700 mt-2">Serena - RF</p>
        </div>
      </div>
    </div>
  </SinglePageLayout>
</template>

<script setup lang="ts">
import { Icon } from '@vicons/utils'
import Social from '@vicons/tabler/social'

import SinglePageLayout from '@/components/layouts/SinglePageLayout.vue'
import LoginForm from '@/components/home/LoginForm.vue'
</script>
