import React, { useState, useRef, useEffect } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import OpenInFullIcon from '@mui/icons-material/OpenInFull';
import Tooltip from '@mui/material/Tooltip';
import styles from './TableWrapper.module.css';


export default function TableWrapper({ children, title, ...props }) {
    const [open, setOpen] = useState(false);
    const [resolvedTitle, setResolvedTitle] = useState(title);

    const tableRef = useRef(null);

    const handleOpen = () => {
        setOpen(true);
    };

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            handleOpen();
        }
    };

    const handleClose = (e) => {
        e.stopPropagation();
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
        },
        '&:focus': {
            color: 'var(--ifm-color-primary-lightest)',
            border: '1px solid var(--ifm-color-primary-lightest)',
        }
    };

    useEffect(() => {
        if (title) return;
        let prev = tableRef.current?.previousElementSibling;
        while (prev) {
            if (prev.tagName === "H2" || prev.tagName === "H3") {
                setResolvedTitle(prev.textContent);
                break;
            }
            prev = prev.previousElementSibling;
        }
    }, [title]);

    return (
        <div tabindex="0"
            className={styles.tableWrapper}
            ref={tableRef}
            role="button"
            onClick={handleOpen}
            onKeyDown={handleKeyDown}
            aria-label="Expand table">

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
                        justifyContent: 'space-between',
                        alignItems: 'center',
                        backgroundColor: 'var(--ifm-background-color)',
                        borderBottom: '1px solid var(--ifm-contents-border-color)'
                    }}
                >
                    <h2 style={{ margin: '0 20px 0 0', color: 'var(--ifm-font-color-base)' }}>
                        {resolvedTitle || ""}
                    </h2>
                    <Tooltip title="Close window">
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
                    <div className="table-container" style={{ overflowX: 'auto', margin: '1rem', display: 'flex', justifyContent: 'center' }}>
                        <table {...props} style={{ width: 'fit-content', margin: '0 auto' }}>
                            {children}
                        </table>
                    </div>
                </DialogContent>
            </Dialog>
        </div>
    );
}
