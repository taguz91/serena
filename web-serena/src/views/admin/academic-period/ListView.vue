<template>
  <NPageHeader
    title="Periodos Académicos"
    subtitle="Revisar, crear, editar y eliminar periodos académicos"
  >
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
import {
  NBadge,
  NButton,
  NDataTable,
  NIcon,
  NPageHeader,
  NSpace,
  type DataTableColumns
} from 'naive-ui'
import { h, onMounted, ref } from 'vue'
import FormView from './FormView.vue'

const show = ref(false)

const showModal = () => {
  show.value = true
}

interface AcademicPeriod {
  name: string
  isActive: boolean
  reference: string
}

const data = ref<AcademicPeriod[]>([])

const columns: DataTableColumns<AcademicPeriod> = [
  {
    title: 'Nombre',
    key: 'name'
  },
  {
    title: 'Estado',
    key: 'isActive',
    render(row) {
      return h(
        NBadge,
        {
          type: row.isActive ? 'success' : 'info',
          value: row.isActive ? 'Activo' : 'Inactivo'
        },
        {}
      )
    }
  },
  {
    title: '',
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
        name: '2021-2022',
        isActive: true,
        reference: '2021-2022'
      },
      {
        name: '2022-2023',
        isActive: false,
        reference: '2022-2023'
      },
      {
        name: '2023-2024',
        isActive: false,
        reference: '2023-2024'
      },
      {
        name: '2024-2025',
        isActive: false,
        reference: '2024-2025'
      },
      {
        name: '2025-2026',
        isActive: false,
        reference: '2025-2026'
      }
    ]
  }, 1000)
})
</script>
