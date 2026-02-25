import React, { useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import OpenInFullIcon from '@mui/icons-material/OpenInFull';
import Tooltip from '@mui/material/Tooltip';
import Box from '@mui/material/Box';


export default function TableWrapper({ children, ...props }) {
    const [open, setOpen] = useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div className="table-wrapper" style={{ position: 'relative', marginBottom: '2rem' }}>
            <Box sx={{ display: 'flex', justifyContent: 'flex-end', mb: 1 }}>
                <Tooltip title="Expand table">
                    <IconButton
                        onClick={handleClickOpen}
                        size="small"
                        color="primary"
                        sx={{
                            backgroundColor: 'var(--ifm-background-surface-color)',
                            border: '1px solid var(--ifm-contents-border-color)',
                            borderRadius: '4px',
                            '&:hover': {
                                backgroundColor: 'var(--ifm-color-primary-lightest)',
                            }
                        }}
                    >
                        <OpenInFullIcon fontSize="small" />
                    </IconButton>
                </Tooltip>
            </Box>

            <div className="table-container" style={{ overflowX: 'auto' }}>
                <table {...props}>
                    {children}
                </table>
            </div>

            <Dialog
                fullWidth
                maxWidth="lg"
                open={open}
                onClose={handleClose}
                scroll="paper"
                aria-labelledby="table-dialog-title"
                PaperProps={{
                    sx: {
                        backgroundColor: 'var(--ifm-background-color)',
                        color: 'var(--ifm-font-color-base)',
                        backgroundImage: 'none'
                    }
                }}
            >
                <DialogTitle
                    id="table-dialog-title"
                    sx={{
                        m: 0,
                        p: 2,
                        display: 'flex',
                        justifyContent: 'flex-end',
                        alignItems: 'center',
                        backgroundColor: 'var(--ifm-background-color)',
                        borderBottom: '1px solid var(--ifm-contents-border-color)'
                    }}
                >
                    <IconButton
                        aria-label="close"
                        onClick={handleClose}
                        sx={{
                            color: 'var(--ifm-font-color-base)',
                            opacity: 0.7,
                            '&:hover': {
                                opacity: 1,
                                backgroundColor: 'var(--ifm-color-primary-lightest)',
                            }
                        }}
                    >
                        <CloseIcon />
                    </IconButton>
                </DialogTitle>
                <DialogContent
                    sx={{
                        backgroundColor: 'var(--ifm-background-color)',
                        p: 0
                    }}
                >
                    <div style={{ overflowX: 'auto', padding: '1rem', display: 'flex', justifyContent: 'center' }}>
                        <table {...props} style={{ width: 'fit-content', margin: '0 auto' }}>
                            {children}
                        </table>
                    </div>
                </DialogContent>
            </Dialog>
        </div>
    );
}
