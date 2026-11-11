// Example apps shown on /showcase, made from webforJ components. Images live
// in static/img/showcase/<slug>/, one full size WebP per screen plus a
// smaller "-thumb" WebP for cards and thumbnails.
//
// The prose goes through translate(), so the list is a function that the page
// calls while it renders. The app names, the component names and the colours
// stay as they are. `platform` is the key the page branches on, `platformLabel`
// is the text it shows. `colours` paint the app's tile and chapter: the base
// colour, a lighter corner, a neighbouring hue and a deep shade. `size` is
// the pixel width of the full and thumb images and the size of one screen.

import { translate } from '@docusaurus/Translate';

const DESKTOP_SIZE = { full: 2880, thumb: 800, width: 1440, height: 900 };
const MOBILE_SIZE = { full: 780, thumb: 390, width: 390, height: 844 };

export function getProjects() {
  return [
    {
      slug: 'freight-dispatch',
      colours: { base: '#1247e6', light: '#2e62f5', neighbour: '#4b2bd6', deep: '#0a2796' },
      name: 'Freight Dispatch',
      platform: 'Desktop',
      platformLabel: translate({
        id: 'page.showcase.freightDispatch.platform',
        message: 'Desktop',
      }),
      size: DESKTOP_SIZE,
      useCase: translate({
        id: 'page.showcase.freightDispatch.useCase',
        message: 'Dispatchers give shipments to drivers and follow every truck on the road.',
      }),
      description: translate({
        id: 'page.showcase.freightDispatch.description',
        message:
          'Operations software for a regional trucking company. Dispatchers match shipments with drivers and trucks, follow every load on the road and handle delays, all day on large screens.',
      }),
      features: [
        {
          title: 'AppLayout',
          text: translate({
            id: 'page.showcase.freightDispatch.features.appLayout.text',
            message: 'A side menu and header that stay in place while the work area changes.',
          }),
        },
        {
          title: 'Drawer',
          text: translate({
            id: 'page.showcase.freightDispatch.features.drawer.text',
            message: 'Shipment details slide in from the side, so the board stays in view.',
          }),
        },
        {
          title: translate({
            id: 'page.showcase.freightDispatch.features.dialogAndTable.title',
            message: 'Dialog and Table',
          }),
          text: translate({
            id: 'page.showcase.freightDispatch.features.dialogAndTable.text',
            message: 'Pick a driver from a sortable table without leaving the board.',
          }),
        },
        {
          title: translate({
            id: 'page.showcase.freightDispatch.features.serverPushedUpdates.title',
            message: 'Server-pushed updates',
          }),
          text: translate({
            id: 'page.showcase.freightDispatch.features.serverPushedUpdates.text',
            message: 'Delays and new arrival times reach every open screen from the server.',
          }),
        },
      ],
      screens: [
        {
          file: '01-dispatch-board',
          name: translate({
            id: 'page.showcase.freightDispatch.screens.dispatchBoard.name',
            message: 'Dispatch board',
          }),
          caption: translate({
            id: 'page.showcase.freightDispatch.screens.dispatchBoard.caption',
            message:
              'Every shipment by status, from waiting for a driver to delivered, with the numbers for today on top.',
          }),
        },
        {
          file: '02-live-map',
          name: translate({
            id: 'page.showcase.freightDispatch.screens.liveMap.name',
            message: 'Live map',
          }),
          caption: translate({
            id: 'page.showcase.freightDispatch.screens.liveMap.caption',
            message: 'Follow every truck on the road and spot late shipments at a glance.',
          }),
        },
        {
          file: '03-load-detail',
          name: translate({
            id: 'page.showcase.freightDispatch.screens.loadDetail.name',
            message: 'Load detail',
          }),
          caption: translate({
            id: 'page.showcase.freightDispatch.screens.loadDetail.caption',
            message:
              'Open a shipment in a side panel to see the customer, the stops and the documents.',
          }),
        },
        {
          file: '04-assign-driver',
          name: translate({
            id: 'page.showcase.freightDispatch.screens.assignDriver.name',
            message: 'Assign driver',
          }),
          caption: translate({
            id: 'page.showcase.freightDispatch.screens.assignDriver.caption',
            message:
              'Pick a driver by hours left, location and trailer. The app explains who fits and who does not.',
          }),
        },
        {
          file: '05-delay-handling',
          name: translate({
            id: 'page.showcase.freightDispatch.screens.delayHandling.name',
            message: 'Delay handling',
          }),
          caption: translate({
            id: 'page.showcase.freightDispatch.screens.delayHandling.caption',
            message: 'Set a new arrival time, write to the customer and let the app tell them.',
          }),
        },
      ],
    },
    {
      slug: 'site-inspection',
      colours: { base: '#0a6a9c', light: '#0f7cab', neighbour: '#0b5fb5', deep: '#063f6b' },
      name: 'Site Inspection',
      platform: 'Mobile',
      platformLabel: translate({ id: 'page.showcase.siteInspection.platform', message: 'Mobile' }),
      size: MOBILE_SIZE,
      useCase: translate({
        id: 'page.showcase.siteInspection.useCase',
        message: 'Building inspectors walk a checklist and get a sign-off, all on a phone.',
      }),
      description: translate({
        id: 'page.showcase.siteInspection.description',
        message:
          'Building safety inspectors follow the route for the day, walk a checklist room by room, photograph problems and get the site manager to sign off. The inspector installs it on the home screen of the phone.',
      }),
      features: [
        {
          title: translate({
            id: 'page.showcase.siteInspection.features.installableApp.title',
            message: 'Installable app',
          }),
          text: translate({
            id: 'page.showcase.siteInspection.features.installableApp.text',
            message:
              'One annotation, @AppProfile, puts the app on the home screen like a native one.',
          }),
        },
        {
          title: 'Upload',
          text: translate({
            id: 'page.showcase.siteInspection.features.upload.text',
            message: 'Photos of each problem go straight into the inspection record.',
          }),
        },
        {
          title: translate({
            id: 'page.showcase.siteInspection.features.serverPushedUpdates.title',
            message: 'Server-pushed updates',
          }),
          text: translate({
            id: 'page.showcase.siteInspection.features.serverPushedUpdates.text',
            message: 'When the office moves an inspection, the phone shows it without a refresh.',
          }),
        },
        {
          title: translate({
            id: 'page.showcase.siteInspection.features.routing.title',
            message: 'Routing',
          }),
          text: translate({
            id: 'page.showcase.siteInspection.features.routing.text',
            message:
              'Each step, from checklist to sign-off, is its own route with a working back button.',
          }),
        },
      ],
      screens: [
        {
          file: '01-todays-inspections',
          name: translate({
            id: 'page.showcase.siteInspection.screens.todaysInspections.name',
            message: 'Today’s inspections',
          }),
          caption: translate({
            id: 'page.showcase.siteInspection.screens.todaysInspections.caption',
            message:
              'See the route for the day, what is done and what is next. Changes from the office show up as they happen.',
          }),
        },
        {
          file: '02-checklist',
          name: translate({
            id: 'page.showcase.siteInspection.screens.checklistForThisBuilding.name',
            message: 'Checklist for this building',
          }),
          caption: translate({
            id: 'page.showcase.siteInspection.screens.checklistForThisBuilding.caption',
            message: 'Walk the building and mark each check as pass, fail or not applicable.',
          }),
        },
        {
          file: '03-record-a-problem',
          name: translate({
            id: 'page.showcase.siteInspection.screens.recordAProblem.name',
            message: 'Record a problem',
          }),
          caption: translate({
            id: 'page.showcase.siteInspection.screens.recordAProblem.caption',
            message: 'Rate how serious it is, add photos and mark them up, then set a fix-by date.',
          }),
        },
        {
          file: '04-inspection-report',
          name: translate({
            id: 'page.showcase.siteInspection.screens.inspectionReport.name',
            message: 'Inspection report',
          }),
          caption: translate({
            id: 'page.showcase.siteInspection.screens.inspectionReport.caption',
            message: 'Review the score, the problems found and the notes before sign-off.',
          }),
        },
        {
          file: '05-sign-off',
          name: translate({
            id: 'page.showcase.siteInspection.screens.signOff.name',
            message: 'Sign-off',
          }),
          caption: translate({
            id: 'page.showcase.siteInspection.screens.signOff.caption',
            message: 'The site manager signs on the phone and the report goes out by email.',
          }),
        },
      ],
    },
    {
      slug: 'clinic-front-desk',
      colours: { base: '#5b34c2', light: '#7446dc', neighbour: '#9a2aa8', deep: '#32177d' },
      name: 'Clinic Front Desk',
      platform: 'Desktop',
      platformLabel: translate({
        id: 'page.showcase.clinicFrontDesk.platform',
        message: 'Desktop',
      }),
      size: DESKTOP_SIZE,
      useCase: translate({
        id: 'page.showcase.clinicFrontDesk.useCase',
        message: 'Reception staff check patients in, book visits and close the day.',
      }),
      description: translate({
        id: 'page.showcase.clinicFrontDesk.description',
        message:
          'Reception at an outpatient clinic with several doctors. Staff see the whole day by clinician, check patients in, book visits, open a patient record and close the payments for the day.',
      }),
      features: [
        {
          title: translate({
            id: 'page.showcase.clinicFrontDesk.features.dataBindingWithValidation.title',
            message: 'Data binding with validation',
          }),
          text: translate({
            id: 'page.showcase.clinicFrontDesk.features.dataBindingWithValidation.text',
            message:
              'Check-in fields are bound to the patient record and flag a bad member number as you type.',
          }),
        },
        {
          title: translate({
            id: 'page.showcase.clinicFrontDesk.features.serverPushedUpdates.title',
            message: 'Server-pushed updates',
          }),
          text: translate({
            id: 'page.showcase.clinicFrontDesk.features.serverPushedUpdates.text',
            message: 'The waiting room updates on every desk the moment a patient checks in.',
          }),
        },
        {
          title: 'Upload',
          text: translate({
            id: 'page.showcase.clinicFrontDesk.features.upload.text',
            message: 'Referral letters and reports attach to the patient record.',
          }),
        },
        {
          title: 'Dialog',
          text: translate({
            id: 'page.showcase.clinicFrontDesk.features.dialog.text',
            message: 'Closing the desk asks for a clear confirmation before anything changes.',
          }),
        },
      ],
      screens: [
        {
          file: '01-today',
          name: translate({
            id: 'page.showcase.clinicFrontDesk.screens.today.name',
            message: 'Today',
          }),
          caption: translate({
            id: 'page.showcase.clinicFrontDesk.screens.today.caption',
            message:
              'The day schedule by clinician next to a waiting room that updates as patients arrive.',
          }),
        },
        {
          file: '02-check-in',
          name: translate({
            id: 'page.showcase.clinicFrontDesk.screens.checkInAPatient.name',
            message: 'Check in a patient',
          }),
          caption: translate({
            id: 'page.showcase.clinicFrontDesk.screens.checkInAPatient.caption',
            message:
              'Find the patient, confirm their details and fix anything that does not pass the checks.',
          }),
        },
        {
          file: '03-book-appointment',
          name: translate({
            id: 'page.showcase.clinicFrontDesk.screens.bookAnAppointment.name',
            message: 'Book an appointment',
          }),
          caption: translate({
            id: 'page.showcase.clinicFrontDesk.screens.bookAnAppointment.caption',
            message:
              'Pick a free time with the right clinician. The app warns about clashes with other visits.',
          }),
        },
        {
          file: '04-patient-record',
          name: translate({
            id: 'page.showcase.clinicFrontDesk.screens.patientRecord.name',
            message: 'Patient record',
          }),
          caption: translate({
            id: 'page.showcase.clinicFrontDesk.screens.patientRecord.caption',
            message:
              'Visits, documents and billing in one place, with an upload for referral letters.',
          }),
        },
        {
          file: '05-end-of-day',
          name: translate({
            id: 'page.showcase.clinicFrontDesk.screens.endOfDay.name',
            message: 'End of day',
          }),
          caption: translate({
            id: 'page.showcase.clinicFrontDesk.screens.endOfDay.caption',
            message: 'Check every payment and see how busy the day was before closing the desk.',
          }),
        },
        {
          file: '06-close-the-day',
          name: translate({
            id: 'page.showcase.clinicFrontDesk.screens.closeTheDay.name',
            message: 'Close the day',
          }),
          caption: translate({
            id: 'page.showcase.clinicFrontDesk.screens.closeTheDay.caption',
            message: 'Confirm what happens to open payments and bookings when the desk closes.',
          }),
        },
      ],
    },
    {
      slug: 'invoice-approval',
      colours: { base: '#0b6b74', light: '#0d7f80', neighbour: '#0b7a57', deep: '#064650' },
      name: 'Invoice Approval',
      platform: 'Desktop',
      platformLabel: translate({
        id: 'page.showcase.invoiceApproval.platform',
        message: 'Desktop',
      }),
      size: DESKTOP_SIZE,
      useCase: translate({
        id: 'page.showcase.invoiceApproval.useCase',
        message: 'The finance team checks invoices against purchase orders and pays them.',
      }),
      description: translate({
        id: 'page.showcase.invoiceApproval.description',
        message:
          'Supplier invoices arrive, get checked against the purchase order, and are approved and paid by the finance team.',
      }),
      features: [
        {
          title: translate({
            id: 'page.showcase.invoiceApproval.features.tableWithSelection.title',
            message: 'Table with selection',
          }),
          text: translate({
            id: 'page.showcase.invoiceApproval.features.tableWithSelection.text',
            message: 'Filter open invoices, select several rows and approve them in one step.',
          }),
        },
        {
          title: translate({
            id: 'page.showcase.invoiceApproval.features.dataBindingWithValidation.title',
            message: 'Data binding with validation',
          }),
          text: translate({
            id: 'page.showcase.invoiceApproval.features.dataBindingWithValidation.text',
            message:
              'Fields read from the invoice are bound to a form that flags totals over the order.',
          }),
        },
        {
          title: 'Dialog',
          text: translate({
            id: 'page.showcase.invoiceApproval.features.dialog.text',
            message:
              'A rejection asks for a reason and a message to the supplier before it goes out.',
          }),
        },
        {
          title: 'Upload',
          text: translate({
            id: 'page.showcase.invoiceApproval.features.upload.text',
            message: 'Signed contracts are uploaded to the supplier page.',
          }),
        },
      ],
      screens: [
        {
          file: '01-inbox',
          name: translate({
            id: 'page.showcase.invoiceApproval.screens.inbox.name',
            message: 'Inbox',
          }),
          caption: translate({
            id: 'page.showcase.invoiceApproval.screens.inbox.caption',
            message:
              'Filter open invoices, select the ones that match their order and approve them together.',
          }),
        },
        {
          file: '02-review-invoice',
          name: translate({
            id: 'page.showcase.invoiceApproval.screens.reviewAnInvoice.name',
            message: 'Review an invoice',
          }),
          caption: translate({
            id: 'page.showcase.invoiceApproval.screens.reviewAnInvoice.caption',
            message: 'Compare the invoice with its purchase order line by line before you decide.',
          }),
        },
        {
          file: '03-approve-or-reject',
          name: translate({
            id: 'page.showcase.invoiceApproval.screens.approveOrReject.name',
            message: 'Approve or reject',
          }),
          caption: translate({
            id: 'page.showcase.invoiceApproval.screens.approveOrReject.caption',
            message:
              'Reject with a reason and a note to the supplier. The approval chain shows who is told.',
          }),
        },
        {
          file: '04-payment-run',
          name: translate({
            id: 'page.showcase.invoiceApproval.screens.paymentRun.name',
            message: 'Payment run',
          }),
          caption: translate({
            id: 'page.showcase.invoiceApproval.screens.paymentRun.caption',
            message:
              'Pick approved invoices for the Friday payment run and see the totals by currency.',
          }),
        },
        {
          file: '05-supplier-detail',
          name: translate({
            id: 'page.showcase.invoiceApproval.screens.supplierDetail.name',
            message: 'Supplier detail',
          }),
          caption: translate({
            id: 'page.showcase.invoiceApproval.screens.supplierDetail.caption',
            message:
              'Invoices, monthly spend and the contract for one supplier, with an upload for the signed renewal.',
          }),
        },
      ],
    },
  ];
}
