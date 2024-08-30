<template>
  <NPageHeader title="Clases" subtitle="Revisar, crear, editar y eliminar clases">
    <template #extra>
      <NSpace>
        <NButton type="primary" @click="showModal">
          <template #icon>
            <NIcon>
              <Plus />
            </NIcon>
          </template>

          Crear nuevo
        </NButton>
      </NSpace>
    </template>

    <NDataTable :columns="columns" :data="data" pagination />
  </NPageHeader>

  <FormView v-model="show" @update:model-value="(value: boolean) => (show = value)" />
</template>

<script setup lang="ts">
import { Pencil, Plus, Trash } from '@vicons/tabler'
import { NButton, NDataTable, NIcon, NPageHeader, NSpace, type DataTableColumns } from 'naive-ui'
import { h, onMounted, ref } from 'vue'
import FormView from './FormView.vue'

const show = ref(false)

const showModal = () => {
  show.value = true
}

interface Classroom {
  academicPeriod: string
  teacher: string
  subject: string
}

const data = ref<Classroom[]>([])

const columns: DataTableColumns<Classroom> = [
  {
    title: 'Periodo académico',
    key: 'academicPeriod'
  },
  {
    title: 'Profesor',
    key: 'teacher'
  },
  {
    title: 'Materia',
    key: 'subject'
  },
  {
    title: 'Acciones',
    key: 'actions',
    width: 125,
    className: 'flex justify-between',
    render: (row) => {
      return [
        h(
          NButton,
          {
            type: 'info',
            tertiary: true,
            onClick: () => {
              console.log('Edit', row)
            }
          },
          {
            icon: () => h(NIcon, null, { default: () => h(Pencil) })
          }
        ),
        h(
          NButton,
          {
            type: 'error',
            tertiary: true,
            onClick: () => {
              console.log('Delete', row)
            }
          },
          {
            icon: () => h(NIcon, null, { default: () => h(Trash) })
          }
        )
      ]
    }
  }
]

onMounted(() => {
  setTimeout(() => {
    data.value = [
      {
        academicPeriod: 'Octubre 2024 - Abril 2025',
        teacher: 'Juan Perez',
        subject: 'Matemáticas'
      },
      {
        academicPeriod: 'Enero 2024 - Julio 2024',
        teacher: 'Maria Rodriguez',
        subject: 'Español'
      },
      {
        academicPeriod: 'Octubre 2024 - Abril 2025',
        teacher: 'Pedro Gomez',
        subject: 'Ciencias'
      },
      {
        academicPeriod: 'Enero 2024 - Julio 2024',
        teacher: 'Ana Perez',
        subject: 'Historia'
      },
      {
        academicPeriod: 'Octubre 2024 - Abril 2025',
        teacher: 'Juan Perez',
        subject: 'Matemáticas'
      },
      {
        academicPeriod: 'Enero 2024 - Julio 2024',
        teacher: 'Maria Rodriguez',
        subject: 'Español'
      },
      {
        academicPeriod: 'Octubre 2024 - Abril 2025',
        teacher: 'Pedro Gomez',
        subject: 'Ciencias'
      },
      {
        academicPeriod: 'Enero 2024 - Julio 2024',
        teacher: 'Ana Perez',
        subject: 'Historia'
      },
      {
        academicPeriod: 'Octubre 2024 - Abril 2025',
        teacher: 'Juan Perez',
        subject: 'Matemáticas'
      },
      {
        academicPeriod: 'Enero 2024 - Julio 2024',
        teacher: 'Maria Rodriguez',
        subject: 'Español'
      },
      {
        academicPeriod: 'Octubre 2024 - Abril 2025',
        teacher: 'Pedro Gomez',
        subject: 'Ciencias'
      },
      {
        academicPeriod: 'Enero 2024 - Julio 2024',
        teacher: 'Ana Perez',
        subject: 'Historia'
      }
    ]
  }, 1000)
})
</script>
