<template>
  <AppLayout>
    <template #side>
      <StudentCheckList />
    </template>

    <DetailContainer current="Registrar">
      <div class="flex w-full h-full justify-center items-center mt-10">
        <div class="bg-white rounded-lg w-96 h-96"></div>
      </div>
    </DetailContainer>
  </AppLayout>
</template>

<script setup lang="ts">
import DetailContainer from '@/components/containers/DetailContainer.vue'
import AppLayout from '@/components/layouts/AppLayout.vue'
import StudentCheckList from '@/components/shared/StudentCheckList.vue'
</script>
