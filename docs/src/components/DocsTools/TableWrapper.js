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

    const iconButtonSx = {
        backgroundColor: 'var(--ifm-background-surface-color)',
        color: 'var(--ifm-color-primary)',
        border: '1px solid var(--ifm-background-surface-color)',
        borderRadius: '4px',
            '&:hover': {
                color: 'var(--ifm-color-primary-lightest)',
                border: '1px solid var(--ifm-color-primary-lightest)',
            }
    };

    return (
        <div className="table-wrapper" style={{ position: 'relative', marginBottom: '2rem', maxWidth: 'fit-content' }}>
            <Box sx={{ display: 'flex', justifyContent: 'flex-end', mb: 1 }}>
                <Tooltip title="Expand table">
                    <IconButton
                        onClick={handleClickOpen}
                        size="small"
                        sx={iconButtonSx}
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
                maxWidth="fit-content"
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
                        paddingBottom: 0,
                        display: 'flex',
                        justifyContent: 'flex-end',
                        alignItems: 'center',
                        backgroundColor: 'var(--ifm-background-color)',
                        borderBottom: '1px solid var(--ifm-contents-border-color)'
                    }}
                > <Tooltip title="Close window">
                    <IconButton
                        aria-label="close"
                        onClick={handleClose}
                        sx={iconButtonSx}
                    >
                        <CloseIcon />
                    </IconButton>
                </Tooltip>
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
