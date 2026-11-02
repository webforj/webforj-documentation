import { createContext, useContext } from 'react';

// How many items the current blog listing holds, so the terminal line above
// the filter can print it as the output of its command.
const BlogListingCountContext = createContext(null);

export const BlogListingCountProvider = BlogListingCountContext.Provider;

export function useBlogListingCount() {
  return useContext(BlogListingCountContext);
}
