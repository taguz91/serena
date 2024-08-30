<template>
  <NPageHeader
    title="Estudiantes"
    subtitle="Sincroniza nuevos estudiantes desde una plantilla excel"
  >
    <NForm>
      <NUpload
        directory-dnd
        accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
      >
        <NUploadDragger>
          <div class="mt-5">
            <NIcon size="48" :depth="3">
              <FilePlus />
            </NIcon>
          </div>

          <p class="text-lg">Clic o suelta el archivo en esta area para subirlo</p>

          <p class="m-2 text-slate-400">
            Solo se permiten archivos en formato .xlsx, no subas información sensible.
          </p>
        </NUploadDragger>
      </NUpload>
    </NForm>
  </NPageHeader>
</template>

<script setup lang="ts">
import { FilePlus } from '@vicons/tabler'
import { NForm, NIcon, NPageHeader, NUpload, NUploadDragger } from 'naive-ui'
</script>
